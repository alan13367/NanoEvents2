package com.example.nanoevents2.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;
import com.example.nanoevents2.view.ChatActivity;
import com.example.nanoevents2.view.SendMessageToUserActivity;
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
    List<User> myMessagesChats;
    UserItemAdapter adapter;//DataManager.getInstance().getUsersMyMessagesUsers();

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
        myMessagesChats = DataManager.getInstance().getUsersMyMessagesUsers();
        RecyclerView recyclerView = view.findViewById(R.id.myMessagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new UserItemAdapter(myMessagesChats, getContext(), View.GONE
                ,View.GONE, "","", new UserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        fab = view.findViewById(R.id.newMessageFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendMessageToUserActivity.class);
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.myMessagesSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyAPISingleton.getUsersMessagingLoggedUser(getContext(), new UserVolleyCallback() {
                    @Override
                    public void onSuccess(String response, Object o) {
                        swipeRefreshLayout.setRefreshing(false);
                        DataManager.getInstance().setUsersMyMessagesUsers((List<User>) o);
                        myMessagesChats = (List<User>) o;
                        adapter = new UserItemAdapter(myMessagesChats, getContext(), View.GONE
                                ,View.GONE, "","", new UserItemAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(User user) {
                                Intent intent = new Intent(getContext(), ChatActivity.class);
                                intent.putExtra("User",user);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });


            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}