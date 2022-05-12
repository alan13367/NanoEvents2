package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;

import java.util.ArrayList;

public class EventRV_Adapter extends RecyclerView.Adapter<EventRV_Adapter.EventViewHolder> implements View.OnClickListener {

    ArrayList<Event> eventArrayList;
    private View.OnClickListener listener;

    public EventRV_Adapter(ArrayList<Event> eventArrayList) {

        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        view.setOnClickListener(this);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.txtName.setText("fgfg" );
        holder.txtDescription.setText(eventArrayList.get(position).getDescription());
        //holder.photo.setImageResource(eventArrayList.get(position).getId());
    }

    public int getItemCount() {
        return eventArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
// VIEW HOLDER

    protected class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDescription;
        public ImageView photo;

        public EventViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.eventNameId);
            txtDescription = itemView.findViewById(R.id.eventDescription);
            photo = itemView.findViewById(R.id.eventImage);
        }
    }




}

