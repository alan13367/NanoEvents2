package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchBox;
    private List<User> userSearch;
    private UserItemAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        setTitle(R.string.search_users);

        Toolbar toolbar = findViewById(R.id.searchUsersToolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.searchUsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()
                , DividerItemDecoration.VERTICAL));
        userSearch = new ArrayList<>();
        recyclerView.setAdapter(new UserItemAdapter(userSearch,getApplicationContext(),View.VISIBLE,"Add"));

        searchBox = findViewById(R.id.searchUserEditText);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !searchBox.getText().toString().isEmpty()) {
                    MyAPISingleton.searchUsersByString(getApplicationContext()
                            , searchBox.getText().toString(), new UserVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            userSearch = (ArrayList<User>) o;
                            if(userSearch.isEmpty()){
                                Toast.makeText(SearchUsersActivity.this, "No user was found matching parameters", Toast.LENGTH_SHORT).show();
                            }
                            recyclerView.setAdapter(new UserItemAdapter(userSearch,getApplicationContext(), View.VISIBLE,"Add"));

                        }

                    });
                    return true;
                }
                return false;
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