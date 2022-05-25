package com.example.nanoevents2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import java.util.ArrayList;
import java.util.List;

public class SendMessageToUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchBox;
    private List<User> userSearch;
    private UserItemAdapter usersAdapter;

    private UserItemAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new UserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                //Open Individual User View
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("User",user);
                DataManager.getInstance().addUserMyMessages(user);
                startActivity(intent);
                finish();
            }
        };
        setContentView(R.layout.activity_search_users);
        setTitle(R.string.select_user);

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
        userSearch = DataManager.getInstance().getFriends();
        refreshAdapter();

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
                                        Toast.makeText(SendMessageToUserActivity.this
                                                , "No user was found matching parameters", Toast.LENGTH_SHORT).show();
                                    }
                                    refreshAdapter();

                                }

                            });
                    return true;
                }
                else if(actionId == EditorInfo.IME_ACTION_SEARCH && searchBox.getText().toString().isEmpty()) {
                    userSearch = DataManager.getInstance().getFriends();
                    refreshAdapter();
                    return true;
                }
                return false;
            }
        });
    }
    private void refreshAdapter(){
        usersAdapter = new UserItemAdapter(userSearch,getApplicationContext()
                , View.GONE,View.GONE,"","",listener);
        recyclerView.setAdapter(usersAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkAlreadyMessaged(User user){
        for (User u:DataManager.getInstance().getUsersMyMessagesUsers()){
        }
        return false;
    }
}
