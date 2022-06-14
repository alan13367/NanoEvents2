package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.EventVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.view.fragments.MyEventFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private TextView startDatePick;
    private Button startDatePickBtn;
    private TextView dateRangeTxt;
    private Button rangeDatePickBtn;
    private Button createEventBtn;
    private EditText eventName, eventDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        List<Event> userEvents = DataManager.getInstance().getAllUserEvents();

        // event type spinner
        Spinner spinner= findViewById(R.id.spinnerEventCategory);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("title");*/
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Sports");
        modes.add("Business");
        modes.add("Party");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,modes);
        spinner.setAdapter(adapter);

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


        // date range
        dateRangeTxt = findViewById(R.id.dateRangeTxt);
        rangeDatePickBtn = findViewById(R.id.rangeDatePickBtn);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();
        rangeDatePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        dateRangeTxt.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        // create event
        eventDescription = findViewById(R.id.setDescriptionEvent);
        eventName = findViewById(R.id.setTitleEvent);
        createEventBtn = findViewById(R.id.createEventBtn);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event newEvent = new Event(eventName.getText().toString(),eventDescription.getText().toString(),dateRangeTxt.getText().toString());
                //MyAPISingleton.createEvent();
                //userEvents.add(newEvent);
                //DataManager.getInstance().setAllUserEvents(userEvents);
               /* MyAPISingleton.createEvent(this, eventName.getText().toString(), "", "", "", "", "",
                        "" ,0, "", new EventVolleyCallback() {
                    @Override
                    public void onSuccess(String response, Object o) {
                        //userEvents=(List<Event>) o;
                    }
                });*/
            }
        });

    }




    //spinner item select
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        // set event category to this
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String startDate = month + "/" + dayOfMonth+"/"+year;
        startDatePickBtn.setText(startDate);
    }
}