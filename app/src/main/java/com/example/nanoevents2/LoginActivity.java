package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.signUpBtn:
                Intent intent1 = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }
}