package com.example.nanoevents2.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.Adapters.EventRV_Adapter;
import com.example.nanoevents2.Adapters.MyEventsAdapter;
import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.view.CreateEventActivity;
import com.example.nanoevents2.view.myEventFrame;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyEventFragment extends Fragment {

    private ArrayList<Event> eventList;
    //add adapter
    FloatingActionButton fab;
    private final EventRV_Adapter.OnItemClickListener listener = new EventRV_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(Event event) {
            //Open Individual Event View
        }
    };

    public static MyEventFragment newInstance() { return new MyEventFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_events,container,false);
        getActivity().setTitle("My Events");
        eventList = (ArrayList<Event>) DataManager.getInstance().getAllUserEvents();
        eventList.add(new Event("test","hola",""));

        RecyclerView eventRv = view.findViewById(R.id.myEventsRecyclerView);
        eventRv.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        eventRv.setAdapter(new EventRV_Adapter(getContext(),eventList,listener));

        fab = view.findViewById(R.id.newEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CREATE EVENT ACTIVITY
                Intent intent = new Intent(getContext(), CreateEventActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }




}
