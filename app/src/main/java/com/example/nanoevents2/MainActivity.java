package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        Bundle bundle = getIntent().getExtras();
        accessToken = bundle.getString("accessToken");
        textView.setText(accessToken);
        getUser(bundle.getString("email"));
        System.out.println(userJson);
    }




    public void getUser(String email){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                MyAPISingleton.searchUser+email,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject user = response.getJSONObject(0);
                            userJson = user.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
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