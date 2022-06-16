package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.persistence.DataManager;
import com.example.nanoevents2.persistence.MyAPISingleton;

import java.util.ArrayList;

public class EventRV_Adapter extends RecyclerView.Adapter<EventRV_Adapter.EventViewHolder> {

    ArrayList<Event> eventArrayList;
    final EventRV_Adapter.OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(Event event);
    }

    public EventRV_Adapter(Context context,ArrayList<Event> eventArrayList,EventRV_Adapter.OnItemClickListener listener) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.txtName.setText(event.getName());
        holder.txtType.setText(event.getType());
        holder.txtDate.setText(event.getDateFormat(event.getDate()));
        MyAPISingleton.getInstance(context).getImageLoader().get(event.getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.photo.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.photo.setImageBitmap(DataManager.getInstance().getDefaultEventImage());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(eventArrayList.get(holder.getBindingAdapterPosition()));
            }
        });
    }

    public int getItemCount() {
        return eventArrayList.size();
    }

    protected class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName,txtType,txtDate;
        public ImageView photo;

        public EventViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.eventNameId);
            photo = itemView.findViewById(R.id.eventImage);
            txtType = itemView.findViewById(R.id.eventType);
            txtDate = itemView.findViewById(R.id.eventDate);
        }
    }




}

