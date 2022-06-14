package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email,name,last_name,password,confirm_password,imageUrl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button createAccBtn = findViewById(R.id.createAccBtn);
        createAccBtn.setOnClickListener(this);

        email = findViewById(R.id.signUpEmailText);
        name = findViewById(R.id.signUpNameText);
        last_name = findViewById(R.id.signUplastNameText);
        password = findViewById(R.id.signUpPasswordText);
        confirm_password = findViewById(R.id.signUpConfirmPasswordText);
        imageUrl = findViewById(R.id.imageLinkSignUp);
        progressBar = findViewById(R.id.pb_signUp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createAccBtn:
                if(fieldsCorrect()){
                    progressBar.setVisibility(View.VISIBLE);
                    MyAPISingleton.signUp(getApplicationContext(),getEditTextString(name)
                            , getEditTextString(last_name), getEditTextString(email), getEditTextString(password)
                            , getEditTextString(imageUrl), new UserVolleyCallback() {

                        @Override
                        public void onSuccess(String response,Object o) {
                            MyAPISingleton.login(getApplicationContext(), getEditTextString(email), getEditTextString(password)
                                    , new UserVolleyCallback() {
                                        @Override
                                        public void onSuccess(String response, Object o) {
                                            Toast.makeText(getApplicationContext(),"SignUp Successful!",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Error Creating Account. Email is already registered",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private String getEditTextString(EditText editText){
        return editText.getText().toString();
    }

    private boolean fieldsCorrect(){
        if(password.length() < 8){
            Toast.makeText(getApplicationContext(),"Passwords must be at least 8 characters long. ",Toast.LENGTH_LONG).show();
            return false;
        }else if(!confirm_password.getText().toString().equals(password.getText().toString())){
            Toast.makeText(getApplicationContext(),"Passwords not matching. ",Toast.LENGTH_LONG).show();
            return false;
        }else if(name.length() < 3 || last_name.length() < 3){
            Toast.makeText(getApplicationContext(),"Name and Last name must be at least 3 characters long. ",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}