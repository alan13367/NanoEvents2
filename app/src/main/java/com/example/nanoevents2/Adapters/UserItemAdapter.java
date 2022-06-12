package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;
import com.example.nanoevents2.persistence.UserVolleyCallback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UserItemVH> {

    private List<User> userList;
    private Context context;
    private int button1Visibility;
    private int button2Visibility;
    private String buttonText;
    private String buttonText2;
    private final UserItemAdapter.OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(User user);
    }

    public UserItemAdapter(List<User> userList, Context context, int button1Visibility,int button2Visibility
            , String buttonText,String buttonText2, UserItemAdapter.OnItemClickListener listener){
        this.userList = userList;
        this.context = context;
        this.button1Visibility = button1Visibility;
        this.button2Visibility = button2Visibility;
        this.buttonText = buttonText;
        this.buttonText2 = buttonText2;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent,false);
        return new UserItemVH(view).linkAdapter(this,button1Visibility,button2Visibility,buttonText,buttonText2);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(userList.get(holder.getBindingAdapterPosition()));
            }
        });

        if(buttonText.equals("Add")){
            holder.button.setOnClickListener(view -> MyAPISingleton.manageFriendRequests(context
                    ,userList.get(holder.getBindingAdapterPosition()).getId()
                    ,User.CREATE_REQUEST, new UserVolleyCallback() {
                @Override
                public void onSuccess(String response, Object o) {
                    Toast.makeText(context, "Friend Request Sent!", Toast.LENGTH_SHORT).show();
                }
            }));
        }else if (buttonText.equals("Accept")){
            holder.button.setTextSize(10);
            holder.button.setOnClickListener(view -> MyAPISingleton.manageFriendRequests(context
                    ,userList.get(holder.getBindingAdapterPosition()).getId()
                    ,User.ACCEPT_REQUEST, new UserVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            userList.remove(holder.getBindingAdapterPosition());
                            holder.adapter.notifyItemRemoved(holder.getBindingAdapterPosition());
                            MyAPISingleton.getFriends(context, new UserVolleyCallback() {
                                @Override
                                public void onSuccess(String response, Object o) {

                                    DataManager.getInstance().setFriends((List<User>) o);
                                }
                            });
                            Toast.makeText(context, "Friend Request Accepted!", Toast.LENGTH_SHORT).show();
                        }
                    }));
        }
        if(buttonText2.equals("Remove")){
            holder.button1.setTextSize(10);
            holder.emailTextView.setVisibility(View.GONE);
            holder.button1.setOnClickListener(view -> MyAPISingleton.manageFriendRequests(context
                    ,userList.get(holder.getBindingAdapterPosition()).getId()
                    ,User.REJECT_REQUEST, new UserVolleyCallback() {
                        @Override
                        public void onSuccess(String response, Object o) {
                            userList.remove(holder.getBindingAdapterPosition());
                            holder.adapter.notifyItemRemoved(holder.getBindingAdapterPosition());
                            MyAPISingleton.getFriendRequests(context, new UserVolleyCallback() {
                                @Override
                                public void onSuccess(String response, Object o) {
                                    DataManager.getInstance().setFriendRequestsList((List<User>) o);
                                }
                            });
                            Toast.makeText(context, "Friend Request Rejected!", Toast.LENGTH_SHORT).show();
                        }
                    }));
        }

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
        private Button button1;

        public UserItemVH(@NonNull View itemView) {
            super(itemView);
            userImageView =itemView.findViewById(R.id.userItemImage);
            nameTextView = itemView.findViewById(R.id.userItemName);
            emailTextView = itemView.findViewById(R.id.userItemEmail);
            button = itemView.findViewById(R.id.userItemButton);
            button1 = itemView.findViewById(R.id.userItemButton2);
        }

        public UserItemVH linkAdapter(UserItemAdapter adapter,int button1Visibility
                ,int button2Visibility,String buttonText,String button2Text) {
            this.adapter = adapter;
            button.setVisibility(button1Visibility);
            button.setText(buttonText);

            button1.setVisibility(button2Visibility);
            button1.setText(button2Text);
            return this;
        }
    }
}

