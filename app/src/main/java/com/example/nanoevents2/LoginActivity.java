package com.example.nanoevents2;

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
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;
import com.example.nanoevents2.utilities.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private String accessToken;
    private EditText emailText;
    private EditText pwdIn;
    private Button logInBtn;
    private Button signUpBtn;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = findViewById(R.id.emailIn);
        pwdIn = findViewById(R.id.pwdIn);

        logInBtn = findViewById(R.id.signInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(new VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        loginSuccess();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getApplicationContext(),"There has been an error in checking " +
                                "your credentials, please try again.",Toast.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.GONE);

            }
        });

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setPaintFlags(signUpBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // underline sign up button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent1);
            }
        });
        progressBar = findViewById(R.id.loginPB);
    }

    private void loginSuccess() {
        Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("accessToken",accessToken);
        intent.putExtra("email",emailText.getText().toString());
        startActivity(intent);
        finish();
    }

    private void login(final VolleyCallback volleyCallback){
        progressBar.setVisibility(View.VISIBLE);
        String email = emailText.getText().toString();
        String password = pwdIn.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            JSONObject body = new JsonBuilder().add("email",email).add("password",password).json;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,MyAPISingleton.login_url,body,
                    response ->{
                        try {
                            accessToken = response.getString("accessToken");
                            volleyCallback.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error ->{
                error.printStackTrace();
                volleyCallback.onFailure();
            });

            MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }else {
            volleyCallback.onFailure();
        }

    }
}