package com.example.nanoevents2.persistence;

import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Event> eventsList;
    private User user;

    private DataManager(){
        eventsList = new ArrayList<>();
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public List<Event> getEventsList(){
        return eventsList;
    }

    public void setEventsList(List<Event> events){
        eventsList = events;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User u){
        user = u;
    }
}
