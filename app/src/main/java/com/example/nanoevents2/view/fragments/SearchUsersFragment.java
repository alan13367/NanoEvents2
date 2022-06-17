package com.example.nanoevents2.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.Adapters.UserItemAdapter;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;
import com.example.nanoevents2.view.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchBox;
    private List<User> userSearch;
    private UserItemAdapter.OnItemClickListener listener;


    public SearchUsersFragment() {
        // Required empty public constructor

    }

    public static SearchUsersFragment newInstance() {
        return new SearchUsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.search_users);
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_users, container
                ,false);

        recyclerView = view.findViewById(R.id.searchUsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        userSearch = new ArrayList<>();
        recyclerView.setAdapter(new UserItemAdapter(userSearch,getContext(),View.VISIBLE,View.GONE
                ,"Add","",listener));

        searchBox = view.findViewById(R.id.searchUserEditText);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !searchBox.getText().toString().isEmpty()) {
                    MyAPISingleton.searchUsersByString(getContext()
                            , searchBox.getText().toString(), new UserVolleyCallback() {
                                @Override
                                public void onSuccess(String response, Object o) {
                                    userSearch = (ArrayList<User>) o;
                                    if(userSearch.isEmpty()){
                                        Toast.makeText(getContext(),"No user was found matching parameters", Toast.LENGTH_SHORT).show();
                                    }
                                    recyclerView.setAdapter(new UserItemAdapter(userSearch,getContext()
                                            , View.VISIBLE,View.GONE,"Add","",listener));

                                }

                            });
                    return true;
                }
                return false;
            }
        });

        listener = new UserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
        };

        return view;

    }
}
