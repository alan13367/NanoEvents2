package com.example.nanoevents2.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestsFragment extends Fragment {

    FloatingActionButton fab;
    List<User> friendRequests;//DataManager.getInstance().getUsersMyMessagesUsers();
    UserItemAdapter adapter;
    public FriendRequestsFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static FriendRequestsFragment newInstance() {
        return new FriendRequestsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.friend_requests);
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_friendrequests, container
                ,false);

        friendRequests = new ArrayList<>();
        friendRequests = DataManager.getInstance().getFriendRequestsList();
        RecyclerView recyclerView = view.findViewById(R.id.friendRequestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new UserItemAdapter(friendRequests, getContext(), View.VISIBLE
                ,View.VISIBLE, "Accept","Remove", new UserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {

            }
        });
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshFriendRequests);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MyAPISingleton.getFriendRequests(getContext(), new UserVolleyCallback() {
                    @Override
                    public void onSuccess(String response, Object o) {
                        swipeRefreshLayout.setRefreshing(false);
                        friendRequests = (List<User>)o;
                        adapter = new UserItemAdapter(friendRequests, getContext(), View.VISIBLE
                                ,View.VISIBLE, "Accept","Remove", new UserItemAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(User user) {

                            }
                        });
                        recyclerView.setAdapter(adapter);
                        if(friendRequests.isEmpty()){
                            Toast.makeText(getContext(),"You have no Friend Requests!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
