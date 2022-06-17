package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanoevents2.R;
import com.example.nanoevents2.persistence.EventVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;

public class EventRatingActivity extends AppCompatActivity {

    private EditText reviewEditText;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rating);
        Toolbar toolbar = findViewById(R.id.ratingEventToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ((TextView)findViewById(R.id.ratingEventTitle)).setText(getIntent().getExtras().getString("Title"));

        reviewEditText = findViewById(R.id.reviewEditText);

        ratingBar = findViewById(R.id.eventRatingBar);
        Button button = findViewById(R.id.reviewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewEditText.getText().toString().isEmpty()){
                    Toast.makeText(EventRatingActivity.this, "Please Enter Your Review."
                            , Toast.LENGTH_SHORT).show();
                }else {
                    MyAPISingleton.rateEventById(getApplicationContext(),getIntent().getExtras().getInt("EventId")
                            , ratingBar.getNumStars(), reviewEditText.getText().toString(), new EventVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            Toast.makeText(EventRatingActivity.this, "Review Successfully Submitted."
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure() {
                            Toast.makeText(EventRatingActivity.this
                                    , "Couldn't post the review, either not assisted or already submitted a review."
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
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