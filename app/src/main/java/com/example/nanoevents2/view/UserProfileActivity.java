package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.model.entities.user.UserStatistics;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        Toolbar toolbar = findViewById(R.id.userProfileToolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        User user = (User) getIntent().getExtras().get("User");

        imageView =findViewById(R.id.imgUser);
        MyAPISingleton.getInstance(getApplicationContext()).getImageLoader().get(user.getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageBitmap(DataManager.getInstance().getDefaultProfileImage());
            }
        });

        nameTextView = findViewById(R.id.nameProfile);
        nameTextView.setText(new StringBuilder().append(user.getName()).append(" ").append(user.getLast_name()).toString());
        emailTextView = findViewById(R.id.emailProfile);
        emailTextView.setText(user.getEmail());


        MyAPISingleton.getUserStatistics(getApplicationContext(), user.getId(), new UserVolleyCallback() {
            @Override
            public void onSuccess(String response, Object o) {
                UserStatistics userStatistics = (UserStatistics) o;
                ((TextView)findViewById(R.id.profileAverageScore))
                        .setText(Double.toString(userStatistics.getAvg_score()));
                ((TextView)findViewById(R.id.profileNumberOfComments))
                        .setText(Integer.toString(userStatistics.getNum_comments()));
                ((TextView)findViewById(R.id.profilePercentageBelow))
                        .setText(userStatistics.getPercentage_commenters_below() +" %");
            }
        });

        ((ImageButton)findViewById(R.id.editProfileButton)).setVisibility(View.GONE);

        ((Button)findViewById(R.id.profileEventAssistancesButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EventAssistanceActivity.class);
                i.putExtra("User",user);
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}