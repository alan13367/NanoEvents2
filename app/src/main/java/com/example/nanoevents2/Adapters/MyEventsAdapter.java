package com.example.nanoevents2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoevents2.R;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.view.fragments.MyEventFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.EventVH> {

    Context context;
    List<Event> eventArrayList;
    final MyEventsAdapter.OnItemClickListener listener;
    int userId;

    public interface OnItemClickListener{
        void onItemClick(Event event);
    }

    public MyEventsAdapter(List<Event> eventArrayList, Context context, MyEventsAdapter.OnItemClickListener listener) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card,parent,false);
        return new EventVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH holder, int position) {
        //holder.imageView.setImageBitmap(eventArrayList.get(position).getImage());
        holder.name.setText(eventArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    class EventVH extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView name ;
        private MyEventsAdapter adapter;

        public EventVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eventImage);
            name = itemView.findViewById(R.id.eventNameId);
        }
        
        public EventVH linkAdapter(MyEventsAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }

}

