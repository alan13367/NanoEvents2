package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nanoevents2.utilities.JsonBuilder;
import com.example.nanoevents2.utilities.MyAPISingleton;
import com.example.nanoevents2.utilities.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createAccBtn;
    private EditText email,name,last_name,password,confirm_password,imageUrl;
    private String userConfirm;
    private String accessToken;

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
                if(fieldsCorrect()){
                    signUp(getEditTextString(name), getEditTextString(last_name), getEditTextString(email)
                            , getEditTextString(password), getEditTextString(imageUrl)
                            , new VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            getAccessToken(getEditTextString(email),getEditTextString(password), ()
                                    -> {
                                Toast.makeText(getApplicationContext(),"SignUp Successful!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                                intent.putExtra("accessToken",accessToken);
                                intent.putExtra("email",getEditTextString(email));
                                startActivity(intent);
                                finish();
                            });
                        }
                        @Override
                        public void onFailure(){
                            Toast.makeText(getApplicationContext(),"Error Creating Account. Email is already registered",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }

    private void signUp(String name, String last_name, String email, String password, String imagePath, final VolleyCallback volleyCallback){
        JSONObject body = new JsonBuilder().add("name",name).add("last_name",last_name).add("email",email).add("password",password).add("image",imagePath).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,MyAPISingleton.signup_url,body,
                response ->{
                    userConfirm = response.toString();
                    volleyCallback.onSuccess();
                }, error ->{
                volleyCallback.onFailure();
        });

        MyAPISingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getAccessToken(String email,String password,final VolleyCallback volleyCallback){
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