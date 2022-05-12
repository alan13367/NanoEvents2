package com.example.nanoevents2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoevents2.Adapters.EventRV_Adapter;

import com.example.nanoevents2.model.entities.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link eventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class eventListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerEvents ;
    ArrayList<Event> eventArrayList;

    public eventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment eventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static eventListFragment newInstance(String param1, String param2) {
        eventListFragment fragment = new eventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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