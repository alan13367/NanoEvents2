package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Message;
import com.example.nanoevents2.persistence.DataManager;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messagesArrayList;
    int userId;

    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    public MessagesAdapter(Context context, ArrayList<Message> messagesArrayList,int userId) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverchatlayout,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(message.getContent());
            viewHolder.timeofmessage.setText(message.getTimeStamp());
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(message.getContent());
            viewHolder.timeofmessage.setText(message.getTimeStamp());
        }
    }


    @Override
    public int getItemViewType(int position) {
        Message message=messagesArrayList.get(position);
        if(userId == message.getUser_id_send())
        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }




    class SenderViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }




}
