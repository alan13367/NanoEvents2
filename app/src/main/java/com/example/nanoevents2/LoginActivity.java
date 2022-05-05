package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameIn;
    private EditText pwdIn;
    private String accessToken;

    private static final String login_url = "http://puigmal.salle.url.edu/api/v2/users/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameIn = findViewById(R.id.usernameIn);
        pwdIn = findViewById(R.id.pwdIn);

        Button logInBtn = findViewById(R.id.signInBtn);
        logInBtn.setOnClickListener(this);

        Button signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setPaintFlags(signUpBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline sign up button
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInBtn:
                login();
                break;
            case R.id.signUpBtn:
                Intent intent1 = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void login(){
        String email = usernameIn.getText().toString();
        String password = pwdIn.getText().toString();
        JSONObject body = new JsonBuilder().add("email",email).add("password",password).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,login_url,body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    accessToken = response.getString("accessToken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}