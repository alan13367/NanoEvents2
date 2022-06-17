package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nanoevents2.Adapters.EventRV_Adapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.EventVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.ArrayList;
import java.util.List;

public class EventAssistanceActivity extends AppCompatActivity {
    private List<Event> eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_assistance);

        Toolbar toolbar = findViewById(R.id.userAssistancesToolbar);
        setSupportActionBar(toolbar);
        User user = (User) getIntent().getExtras().get("User");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle("Events Assistance");
        RecyclerView eventRv = findViewById(R.id.eventAssistancesRV);
        eventRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        eventRv.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        if(user.getId() == DataManager.getInstance().getUser().getId()){
            eventList = (ArrayList<Event>) DataManager.getInstance().getUserEventAssistance();
            eventRv.setAdapter(new EventRV_Adapter(getApplicationContext(), (ArrayList<Event>) eventList, new EventRV_Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(Event event) {

                }
            }));
        }else {
            MyAPISingleton.getUserEventsAssistance(getApplicationContext(), user.getId(), Event.FUTURE_EVENT, new EventVolleyCallback() {
                @Override
                public void onSuccess(String response, Object o) {
                    eventList = (ArrayList<Event>) o;
                    eventRv.setAdapter(new EventRV_Adapter(getApplicationContext(), (ArrayList<Event>) eventList, new EventRV_Adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Event event) {

                        }
                    }));
                }
            });
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

}