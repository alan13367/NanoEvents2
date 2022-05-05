package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createAccBtn;
    private EditText email,name,last_name,password,confirm_password,imageUrl;
    private String userConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        createAccBtn = findViewById(R.id.createAccBtn);
        createAccBtn.setOnClickListener(this);

        email = findViewById(R.id.signUpEmailText);
        name = findViewById(R.id.signUpNameText);
        last_name = findViewById(R.id.signUplastNameText);
        password = findViewById(R.id.signUpPasswordText);
        confirm_password = findViewById(R.id.signUpConfirmPasswordText);
        imageUrl = findViewById(R.id.imageLinkSignUp);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createAccBtn:
                if(confirm_password.getText().toString().equals(password.getText().toString())){
                    signUp(getEditTextString(name),getEditTextString(last_name),getEditTextString(email),getEditTextString(password),getEditTextString(imageUrl));
                }
                break;
        }
    }

    private void signUp(String name,String last_name,String email,String password,String imagePath){
        JSONObject body = new JsonBuilder().add("name",name).add("last_name",last_name).add("email",email).add("password",password).add("image",imagePath).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,MyAPISingleton.signup_url,body,
                response ->{
                    userConfirm = response.toString();
                }, error ->{

        });

        MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getEditTextString(EditText editText){
        return editText.getText().toString();
    }
}