package com.example.nanoevents2.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.view.UserProfile;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileFragment extends Fragment {

    public MyProfileFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_profile);
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_profile, container
                ,false);
        User user = DataManager.getInstance().getUser();
        ((CircleImageView)view.findViewById(R.id.imgUser)).setImageBitmap(DataManager.getInstance().getUserProfileImage());
        TextView name = view.findViewById(R.id.nameProfile);
        name.setText(new StringBuilder().append(user.getName()).append(" ").append(user.getLast_name()).toString());
        ((TextView)view.findViewById(R.id.emailProfile)).setText(user.getEmail());
        return view;
    }
}
