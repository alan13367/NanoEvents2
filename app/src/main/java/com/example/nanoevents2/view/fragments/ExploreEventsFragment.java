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

import com.example.nanoevents2.Adapters.EventRV_Adapter;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.EventVolleyCallback;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.ArrayList;

public class ExploreEventsFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerEvents ;
    private ArrayList<Event> eventArrayList;
    private ArrayList<Event> filteredArraylist;
    private final EventRV_Adapter.OnItemClickListener listener = new EventRV_Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(Event event) {
            //Open Individual Event View
        }
    };

    public ExploreEventsFragment() {
        // Required empty public constructor
    }

    public static ExploreEventsFragment newInstance() {
       return new ExploreEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Explore Events");
        filteredArraylist = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        eventArrayList = (ArrayList<Event>) DataManager.getInstance().getFutureEventsList();
        recyclerEvents = (RecyclerView) view.findViewById(R.id.exploreEventsRecyclerView);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerEvents.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        EventRV_Adapter adapter = new EventRV_Adapter(getContext(),eventArrayList,listener);
        recyclerEvents.setAdapter(adapter);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.exploreEventsSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MyAPISingleton.getAllFutureEvents(getContext(), new EventVolleyCallback() {
                    @Override
                    public void onSuccess(String response, Object o) {
                        DataManager.getInstance().setFutureEventsList(new ArrayList<>((ArrayList<Event>)o));
                        eventArrayList = (ArrayList<Event>) DataManager.getInstance().getFutureEventsList();
                        recyclerEvents.setAdapter(new EventRV_Adapter(getContext(),eventArrayList,listener));
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure() {
                        recyclerEvents.setAdapter(new EventRV_Adapter(getContext(),eventArrayList,listener));
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        view.findViewById(R.id.bSports).setOnClickListener(this);
        view.findViewById(R.id.bCryptos).setOnClickListener(this);
        view.findViewById(R.id.bEducation).setOnClickListener(this);
        view.findViewById(R.id.bGames).setOnClickListener(this);
        view.findViewById(R.id.bBusiness).setOnClickListener(this);
        view.findViewById(R.id.bTechnology).setOnClickListener(this);
        view.findViewById(R.id.bParty).setOnClickListener(this);
        view.findViewById(R.id.bFashion).setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void filterRecyclerView(String type){
        filteredArraylist.clear();
        for(Event e:eventArrayList){
            if(e.getType().equals(type)){
                filteredArraylist.add(e);
            }
        }
        recyclerEvents.setAdapter(new EventRV_Adapter(getContext(),filteredArraylist,listener));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bSports:
                filterRecyclerView("Sports");
                break;
            case R.id.bCryptos:
                filterRecyclerView("Cryptos");
                break;
            case R.id.bBusiness:
                filterRecyclerView("Business");
                break;
            case R.id.bGames:
                filterRecyclerView("Games");
                break;
            case R.id.bFashion:
                filterRecyclerView("Fashion");
                break;
            case R.id.bTechnology:
                filterRecyclerView("Technology");
                break;
            case R.id.bParty:
                filterRecyclerView("Party");
                break;
            case R.id.bEducation:
                filterRecyclerView("Education");
                break;
        }
    }
}