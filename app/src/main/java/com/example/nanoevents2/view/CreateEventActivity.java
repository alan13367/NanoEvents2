package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateRangeTxt;
    private EditText eventName, eventDescription,eventImageUrl;
    private int startHour, startMinute, endHour, endMinute;
    private boolean datePicked;
    private String eventType;
    private ArrayList<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Creating an Event");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = findViewById(R.id.createEventToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        types = new ArrayList<>();
        types.add("");
        types.add("Sports");
        types.add("Business");
        types.add("Cryptos");
        types.add("Games");
        types.add("Fashion");
        types.add("Technology");
        types.add("Party");
        types.add("Education");
        eventType = "";
        // event type spinner
        Spinner spinner= findViewById(R.id.spinnerEventCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,types);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eventType = types.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eventType = "";
            }
        });

        Context context = this;
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog startTimePicker = new TimePickerDialog(context,style, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedStartHour, int selectedStartMinute) {
                startHour = selectedStartHour;
                startMinute = selectedStartMinute;
                TimePickerDialog endTimePicker = new TimePickerDialog(context, style,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedEndHour, int selectedEndMinute) {
                        endHour = selectedEndHour;
                        endMinute = selectedEndMinute;
                        datePicked = true;
                    }
                },endHour,endMinute,true);
                endTimePicker.setTitle("Select Ending Time");
                endTimePicker.show();
            }
        },startHour,startMinute,true);
        startTimePicker.setTitle("Select Starting Time");



        // date range
        dateRangeTxt = findViewById(R.id.dateRangeTextView);
        Button rangeDatePickBtn = findViewById(R.id.rangeDatePickBtn);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();
        rangeDatePickBtn.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                dateRangeTxt.setText(materialDatePicker.getHeaderText());
                startTimePicker.show();
            });

        });

        // create event
        eventDescription = findViewById(R.id.eventDescriptionEdTx);
        eventName = findViewById(R.id.eventTitleEdTx);
        eventImageUrl = findViewById(R.id.eventImageLinkEdTxt);

        Button locationButton = findViewById(R.id.locationPickBtn);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button createEventBtn = findViewById(R.id.createEventBtn);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldsCorrect()){
                    finish();
                }
            }
        });
    }

    private boolean fieldsCorrect(){
        if(eventName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter an Event Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(eventDescription.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter an Event Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(eventImageUrl.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter an Event image link", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!datePicked){
            Toast.makeText(this, "Please pick an Event Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(eventType.equals("")){
            Toast.makeText(this, "Please select an Event Type", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String startDate = month + "/" + dayOfMonth+"/"+year;
    }
}