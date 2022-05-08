package com.example.nanoevents2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nanoevents2.entities.User;
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;
import com.example.nanoevents2.utilities.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private String accessToken;
    private String userJson;
    private User user;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        accessToken = bundle.getString("accessToken");

        //textView = findViewById(R.id.text);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getUser(bundle.getString("email"), new VolleyCallback() {
            @Override
            public void onSuccess() {
               // textView.setText(userJson);
                user = new Gson().fromJson(userJson,User.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    public void getUser(String email, final VolleyCallback volleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                MyAPISingleton.searchUser+email,null,
                response -> {
                    try {
                        JSONObject user = response.getJSONObject(0);
                        userJson = user.toString();
                        volleyCallback.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> volleyCallback.onFailure()) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }

        };
        MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
}

