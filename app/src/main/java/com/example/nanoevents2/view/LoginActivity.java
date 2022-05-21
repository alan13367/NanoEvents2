package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.utilities.JsonBuilder;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText pwdIn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = findViewById(R.id.emailIn);
        pwdIn = findViewById(R.id.pwdIn);
        progressBar = findViewById(R.id.pb_login);

        Button logInBtn = findViewById(R.id.signInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                MyAPISingleton.login(getApplicationContext(),emailText.getText().toString()
                        ,pwdIn.getText().toString(), new UserVolleyCallback() {
                    @Override
                    public void onSuccess(String response,Object o) {
                        loginSuccess();
                    }
                    @Override
                    public void onFailure() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"There has been an error in checking " +
                                "your credentials, please try again.",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        Button signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setPaintFlags(signUpBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline sign up button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void loginSuccess() {
        Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


}