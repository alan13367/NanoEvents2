package com.example.nanoevents2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.nanoevents2.utilities.MyAPISingleton;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        textView.setText(MyAPISingleton.getInstance(getApplicationContext()).getAccessToken("alan133675@gmail.com","XNOtoTheMoon"));

    }
}