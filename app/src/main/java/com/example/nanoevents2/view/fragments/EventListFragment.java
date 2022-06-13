package com.example.nanoevents2.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoevents2.Adapters.EventRV_Adapter;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;

import java.util.ArrayList;

public class EventListFragment extends Fragment {

    RecyclerView recyclerEvents ;
    ArrayList<Event> eventArrayList;

    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance() {
       return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void fillEventList(){
        eventArrayList.add(new Event("test","","test"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        eventArrayList = new ArrayList<>();
        fillEventList();
        recyclerEvents = (RecyclerView) view.findViewById(R.id.mainRecyclerID);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        EventRV_Adapter adapter = new EventRV_Adapter(eventArrayList);
        recyclerEvents.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}