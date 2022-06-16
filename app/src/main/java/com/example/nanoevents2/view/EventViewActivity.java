package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;

public class EventViewActivity extends AppCompatActivity {

    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        event = (Event) getIntent().getExtras().get("Event");
        Toolbar toolbar = findViewById(R.id.eventViewToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ((TextView)findViewById(R.id.eventTitle)).setText(event.getName());

        MyAPISingleton.getInstance(getApplicationContext()).getImageLoader().get(event.getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                ((ImageView)findViewById(R.id.eventImage)).setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ((ImageView)findViewById(R.id.eventImage)).setImageBitmap(DataManager.getInstance().getDefaultEventImage());
            }
        });

        ((TextView)findViewById(R.id.eventDetails)).setText(new StringBuilder()
                .append("Number of Participants: ")
                .append(event.getN_participators()));

        ((TextView)findViewById(R.id.eventDate)).setText(new StringBuilder().append("Date: ")
                .append(event.getDateFormat(event.getDate())));

        ((TextView)findViewById(R.id.eventStartEndDate)).setText(new StringBuilder()
                .append("\nStart Date: ")
                .append(event.getDateFormat(event.getEventStart_date()))
                .append(" at ")
                .append(event.getTimeFormat(event.getEventStart_date()))
                .append("\n")
                .append("End Date: ")
                .append(event.getDateFormat(event.getEventEnd_date()))
                .append(" at ")
                .append(event.getTimeFormat(event.getEventEnd_date()))
                .append("\n"));

        ((TextView)findViewById(R.id.eventDescription)).setText(new StringBuilder().append("\n\n\n")
                .append(event.getDescription()));

        if(event.getOwner_id() == DataManager.getInstance().getUser().getId()){
            ((Button)findViewById(R.id.ratingButton)).setText(R.string.delete);
            ((Button)findViewById(R.id.ratingButton)).setBackgroundColor(getResources().getColor(R.color.red));
        }
        ((Button)findViewById(R.id.ratingButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(event.getOwner_id() != DataManager.getInstance().getUser().getId()){
                    Intent intent = new Intent(getApplicationContext(),EventRatingActivity.class);
                    intent.putExtra("EventId",event.getId());
                    startActivity(intent);
                }else{
                    //Delete Event
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}


