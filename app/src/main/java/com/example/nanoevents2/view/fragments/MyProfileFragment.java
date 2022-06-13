package com.example.nanoevents2.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.model.entities.user.UserStatistics;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;
import com.example.nanoevents2.view.EditProfileActivity;
import com.example.nanoevents2.view.UserProfile;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileFragment extends Fragment {

    public MyProfileFragment() {
        // Required empty public constructor

    }

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

        MyAPISingleton.getUserStatistics(getContext(), user.getId(), new UserVolleyCallback() {
            @Override
            public void onSuccess(String response, Object o) {
                UserStatistics userStatistics = (UserStatistics) o;
                ((TextView)view.findViewById(R.id.profileAverageScore))
                        .setText(Double.toString(userStatistics.getAvg_score()));
                ((TextView)view.findViewById(R.id.profileNumberOfComments))
                        .setText(Integer.toString(userStatistics.getNum_comments()));
                ((TextView)view.findViewById(R.id.profilePercentageBelow))
                        .setText(userStatistics.getPercentage_commenters_below() +" %");
            }
        });

        ((ImageButton)view.findViewById(R.id.editProfileButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditProfileActivity.class);
                i.putExtra("User",user);
                startActivity(i);
            }
        });
        return view;
    }
}
