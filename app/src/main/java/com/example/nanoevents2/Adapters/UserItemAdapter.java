package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UserItemVH> {

    List<User> userList;
    Context context;
    int buttonVisibility;
    String buttonText;

    public UserItemAdapter(List<User> userList, Context context,int buttonVisibility,String buttonText){
        this.userList = userList;
        this.context = context;
        this.buttonVisibility = buttonVisibility;
        this.buttonText = buttonText;
    }

    @NonNull
    @Override
    public UserItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent,false);
        return new UserItemVH(view).linkAdapter(this,buttonVisibility,buttonText);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemVH holder, int position) {
        holder.nameTextView.setText(userList.get(position).getName());
        holder.emailTextView.setText(userList.get(position).getEmail());
        MyAPISingleton.getInstance(context).getImageLoader().get(userList.get(position).getImage()
                , new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        holder.userImageView.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.userImageView.setImageBitmap(DataManager.getInstance().getDefaultProfileImage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserItemVH extends RecyclerView.ViewHolder{
        CircleImageView userImageView;
        TextView nameTextView;
        TextView emailTextView;
        private UserItemAdapter adapter;
        private Button button;

        public UserItemVH(@NonNull View itemView) {
            super(itemView);
            userImageView =itemView.findViewById(R.id.userItemImage);
            nameTextView = itemView.findViewById(R.id.userItemName);
            emailTextView = itemView.findViewById(R.id.userItemEmail);
            button = itemView.findViewById(R.id.userItemButton);
        }

        public UserItemVH linkAdapter(UserItemAdapter adapter,int buttonVisibility,String buttonText) {
            this.adapter = adapter;
            button.setVisibility(buttonVisibility);
            button.setText(buttonText);
            return this;
        }
    }
}

