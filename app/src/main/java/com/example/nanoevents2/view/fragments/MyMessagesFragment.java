package com.example.nanoevents2.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoevents2.Adapters.MyMessagesAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMessagesFragment extends Fragment {


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
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_messages
                , container, false);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMessages);

        myMessagesChats = new ArrayList<>();
        for(int i = 0;i<10;i++){
            myMessagesChats.add(new User("Name"+i,"https://i.imgur.com/Muy92vw.png"));
        }
        RecyclerView recyclerView = view.findViewById(R.id.myMessagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyMessagesAdapter(myMessagesChats,getContext()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //Call API for new Data
                myMessagesChats = new ArrayList<>();
                for(int i = 0;i<10;i++){
                    myMessagesChats.add(new User("Refreshed "+i,"https://i.imgur.com/Muy92vw.png"));
                    recyclerView.setAdapter(new MyMessagesAdapter(myMessagesChats,getContext()));
                }
            }
        });
        return view;
    }
}