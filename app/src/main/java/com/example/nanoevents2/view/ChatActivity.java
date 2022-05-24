package com.example.nanoevents2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.Adapters.MessagesAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Message;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MessageVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    ArrayList<Message> messages;
    RecyclerView messagesRecyclerView;
    MessagesAdapter messagesAdapter;

    androidx.appcompat.widget.Toolbar toolbar;
    EditText messageEditText;
    ImageButton sendButton;

    User receiverUser;

    TextView nameReceiver;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Top Part
        receiverUser = (User) getIntent().getExtras().getSerializable("User");
        userImage = findViewById(R.id.userImageChat);
        MyAPISingleton.getInstance(getApplicationContext()).getImageLoader().get(receiverUser.getImage()
                , new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        userImage.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userImage.setImageBitmap(DataManager.getInstance().getDefaultProfileImage());
                    }
                });
        nameReceiver = findViewById(R.id.nameUserChat);
        nameReceiver.setText(receiverUser.getName());

        toolbar = findViewById(R.id.chatToolBar);
        getSupportActionBar();

        ImageButton backButton = findViewById(R.id.backButtonChat);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //RecyclerView
        int userId = DataManager.getInstance().getUser().getId();
        messages = new ArrayList<>();
        messages.add(new Message("Hola",userId,809,"18:40"));
        messages.add(new Message("Hola",809,userId,"18:40"));
        messages.add(new Message("Que Tal?",809,userId,"18:40"));
        messages.add(new Message("Muy Bien Tu?",userId,809,"18:40"));
        messages.add(new Message("Sheeesh",userId,809,"18:40"));

        messagesRecyclerView = findViewById(R.id.chatRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(ChatActivity.this, messages, DataManager.getInstance().getUser().getId());
        messagesRecyclerView.setAdapter(messagesAdapter);

        MyAPISingleton.getInstance(getApplicationContext()).getMessagesChatFromUser(receiverUser.getId()
                , new MessageVolleyCallback() {
                    @Override
                    public void onSuccess(String response, Object o) {
                        messages = (ArrayList<Message>) o;
                        messagesAdapter = new MessagesAdapter(ChatActivity.this, messages
                                , DataManager.getInstance().getUser().getId());
                        messagesRecyclerView.setAdapter(messagesAdapter);
                        messagesAdapter.notifyDataSetChanged();
                    }
                });

        //Bottom Part

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredMessage = messageEditText.getText().toString();
                if (enteredMessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                } else {
                    MyAPISingleton.sendMessageToUser(getApplicationContext(), receiverUser.getId()
                            , enteredMessage, new MessageVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            messages.add((Message) o);
                            messagesAdapter = new MessagesAdapter(getApplicationContext(),messages
                                    ,DataManager.getInstance().getUser().getId());
                            messagesRecyclerView.setAdapter(messagesAdapter);
                            messagesAdapter.notifyDataSetChanged();
                        }
                    });
                    messageEditText.setText(null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null)
        {
            messagesAdapter.notifyDataSetChanged();
        }
    }
}