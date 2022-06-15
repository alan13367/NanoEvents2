package com.example.nanoevents2.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
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

    private TextView nameTextView;
    private TextView emailTextView;
    private CircleImageView imageView;
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
        imageView =view.findViewById(R.id.imgUser);
        imageView.setImageBitmap(DataManager.getInstance().getUserProfileImage());
        nameTextView = view.findViewById(R.id.nameProfile);
        nameTextView.setText(new StringBuilder().append(user.getName()).append(" ").append(user.getLast_name()).toString());
        emailTextView = view.findViewById(R.id.emailProfile);
        emailTextView.setText(user.getEmail());
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

    @Override
    public void onResume() {
        super.onResume();
        User user = DataManager.getInstance().getUser();
        MyAPISingleton.getInstance(getContext()).getImageLoader().get(DataManager.getInstance()
                .getUser().getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                DataManager.getInstance().setUserProfileImage(response.getBitmap());
                imageView.setImageBitmap(DataManager.getInstance().getUserProfileImage());
                nameTextView.setText(new StringBuilder()
                        .append(user.getName())
                        .append(" ")
                        .append(user.getLast_name()).toString());
                emailTextView.setText(user.getEmail());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.getInstance().setUserProfileImage(DataManager.getInstance().getDefaultProfileImage());
                imageView.setImageBitmap(DataManager.getInstance().getUserProfileImage());
                nameTextView.setText(new StringBuilder()
                        .append(user.getName())
                        .append(" ")
                        .append(user.getLast_name()).toString());
                emailTextView.setText(user.getEmail());
            }
        });

    }
}
