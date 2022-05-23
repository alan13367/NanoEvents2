package com.example.nanoevents2.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMessagesFragment extends Fragment {

    FloatingActionButton fab;
    List<User> myMessagesChats; //DataManager.getInstance().getUsersMyMessagesUsers();

    public MyMessagesFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static MyMessagesFragment newInstance() {
        return new MyMessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_messages);
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_messages
                , container, false);

        myMessagesChats = new ArrayList<>();
        for(int i = 0;i<10;i++){
            myMessagesChats.add(new User("Name"+i,"https://i.imgur.com/Muy92vw.png"));
        }
        RecyclerView recyclerView = view.findViewById(R.id.myMessagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new UserItemAdapter(myMessagesChats,getContext()));

        fab = view.findViewById(R.id.newMessageFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}