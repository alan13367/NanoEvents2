package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.nanoevents2.R;
import com.example.nanoevents2.view.fragments.MyEventFragment;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private TextView startDatePick;
    private Button startDatePickBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // event type spinner
        Spinner spinner= (Spinner) findViewById(R.id.spinnerEventCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("title");

        // back arrow
        setContentView(R.layout.activity_create_event);
        ImageButton goBack = findViewById(R.id.backButtonCreateEvent);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MyEventFragment.class);
                startActivity(intent);

            }
        });

        // start date
        startDatePickBtn = findViewById(R.id.startDatePickBtn);
        startDatePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        // set event category to this
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ); datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth+"/"+year;
        startDatePickBtn.setText(date);
    }
}