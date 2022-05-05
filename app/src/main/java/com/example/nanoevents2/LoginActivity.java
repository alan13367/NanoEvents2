package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String accessToken;

    private EditText emailText;
    private EditText pwdIn;
    private Button logInBtn;
    private Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = findViewById(R.id.emailIn);
        pwdIn = findViewById(R.id.pwdIn);

        logInBtn = findViewById(R.id.signInBtn);
        logInBtn.setOnClickListener(this);

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setPaintFlags(signUpBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline sign up button
        signUpBtn.setOnClickListener(this);
        accessToken = "failed";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInBtn:
                login();
                checkLogin();
                break;
            case R.id.signUpBtn:
                Intent intent1 = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void checkLogin() {
        if(!accessToken.equals("failed")){
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            intent.putExtra("accessToken",accessToken);
            intent.putExtra("email",emailText.getText().toString());
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Credentials are wrong please try logging in again.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void login(){
        String email = emailText.getText().toString();
        String password = pwdIn.getText().toString();

        JSONObject body = new JsonBuilder().add("email",email).add("password",password).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,MyAPISingleton.login_url,body,
                response ->{
                    try {
                        accessToken = response.getString("accessToken");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error ->{
            accessToken = "failed";
        });

        MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}