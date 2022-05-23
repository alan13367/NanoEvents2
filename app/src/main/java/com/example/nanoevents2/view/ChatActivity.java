package com.example.nanoevents2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nanoevents2.Adapters.MessagesAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Message;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.MessageVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    ArrayList<Message> messages;
    RecyclerView messagesRecyclerView;
    MessagesAdapter messagesAdapter;

    EditText messageEditText;
    ImageButton sendButton;

    User receiverUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiverUser = (User) getIntent().getExtras().getSerializable("User");
        messages = new ArrayList<>();
        MyAPISingleton.getInstance(getApplicationContext()).getMessagesChatFromUser(receiverUser.getId()
                , new MessageVolleyCallback() {
            @Override
            public void onSuccess(String response, Object o) {
                messages = (ArrayList<Message>) o;
                messagesAdapter = new MessagesAdapter(ChatActivity.this,messages);
                messagesRecyclerView.setAdapter(messagesAdapter);
            }
        });

        messagesRecyclerView = findViewById(R.id.chatRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(ChatActivity.this,messages);
        messagesRecyclerView.setAdapter(messagesAdapter);





    }
}