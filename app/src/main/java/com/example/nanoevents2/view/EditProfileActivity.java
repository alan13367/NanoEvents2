package com.example.nanoevents2.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.model.utilities.JsonBuilder;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EditProfileActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText imageUrlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle(R.string.editProfile);
        User user = (User) getIntent().getExtras().getSerializable("User");
        Toolbar toolbar = findViewById(R.id.editProfileToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        imageUrlEditText = findViewById(R.id.imageEditText);

        ((Button)findViewById(R.id.editProfileButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonBuilder jsonBuilder = new JsonBuilder();
                if(!checkEditTextEmpty(nameEditText)){
                    jsonBuilder.add("name",nameEditText.getText().toString());
                }
                if(!checkEditTextEmpty(lastNameEditText)){
                    jsonBuilder.add("last_name",lastNameEditText.getText().toString());
                }
                if(!checkEditTextEmpty(emailEditText)){
                    jsonBuilder.add("email",emailEditText.getText().toString());
                }
                if(!checkEditTextEmpty(passwordEditText)){
                    jsonBuilder.add("password",passwordEditText.getText().toString());
                }
                if(!checkEditTextEmpty(imageUrlEditText)){
                    jsonBuilder.add("image",imageUrlEditText.getText().toString());
                }
                JSONObject jsonObject = jsonBuilder.json;
                if(jsonObject.length() != 0){
                    MyAPISingleton.editUserParameters(getApplicationContext(), jsonObject, new UserVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            Toast.makeText(getApplicationContext(),
                                    "Profile Successfully edited!",Toast.LENGTH_LONG).show();

                            MyAPISingleton.searchUsersByString(getApplicationContext()
                                    , user.getEmail(), new UserVolleyCallback() {
                                @Override
                                public void onSuccess(String response, Object o) {
                                    DataManager.getInstance().setUser(((ArrayList<User>)o).get(0));
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't edit the user parameters one or more fields " +
                                            "is wrong.",Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "All Fields are empty.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean checkEditTextEmpty(EditText editText){
        return editText.getText().toString().isEmpty();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
