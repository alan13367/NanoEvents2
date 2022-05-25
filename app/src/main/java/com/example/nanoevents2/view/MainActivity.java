package com.example.nanoevents2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.view.fragments.FriendRequestsFragment;
import com.example.nanoevents2.view.fragments.MyMessagesFragment;
import com.example.nanoevents2.R;
import com.example.nanoevents2.view.fragments.eventListFragment;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView;
    private DrawerLayout drawer;
    private ArrayList<Event> eventsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView = findViewById(R.id.text);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        //Nav Header Configuration
        User u = DataManager.getInstance().getUser();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundResource(R.color.blueCatSalud);
        View headerView = navigationView.getHeaderView(0);
        TextView navName = (TextView) headerView.findViewById(R.id.nameDrawerHeader);
        navName.setText(new StringBuilder()
                .append(u.getName()).append(" ").append(u.getLast_name()).toString());
        TextView navEmail = (TextView) headerView.findViewById(R.id.emailDrawerHeader);
        navEmail.setText(u.getEmail());
        CircleImageView navImage = headerView.findViewById(R.id.userProfileImage);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile);
        DataManager.getInstance().setDefaultProfileImage(bitmap);
        MyAPISingleton.getInstance(getApplicationContext()).getImageLoader().get(u.getImage()
                , new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                navImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                navImage.setImageBitmap(DataManager.getInstance().getDefaultProfileImage());
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_exploreEvents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new eventListFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_MyMessages:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,MyMessagesFragment.newInstance()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_SearchUsers:
                Intent intent = new Intent(this,SearchUsersActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_FriendRequests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FriendRequestsFragment.newInstance()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_Logout:
                new AlertDialog.Builder(this)
                        .setTitle("Logging Out")
                        .setMessage("Are you sure you want to Log Out?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DataManager.clearDataManager();
                                Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent1);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }


}

