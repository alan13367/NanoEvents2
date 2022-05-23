package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyMessagesAdapter extends RecyclerView.Adapter<MyMessagesAdapter.MyMessagesVH> {

    List<User> userList;
    Context context;

    public MyMessagesAdapter(List<User> userList, Context context){
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyMessagesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_messages_item,parent,false);
        return new MyMessagesVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMessagesVH holder, int position) {
        holder.nameTextView.setText(userList.get(position).getName());
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

    class MyMessagesVH extends RecyclerView.ViewHolder{
        CircleImageView userImageView;
        TextView nameTextView;
        private MyMessagesAdapter adapter;

        public MyMessagesVH(@NonNull View itemView) {
            super(itemView);
            userImageView =itemView.findViewById(R.id.myMessagesImage);
            nameTextView = itemView.findViewById(R.id.myMessagesName);
        }

        public MyMessagesVH linkAdapter(MyMessagesAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}

