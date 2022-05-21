package com.example.nanoevents2.persistence;

import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Event> futureEventsList;
    private User user;
    private List<User> friendRequests;

    private DataManager(){
        futureEventsList = new ArrayList<>();
        friendRequests = new ArrayList<>();
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public List<Event> getFutureEventsList(){
        return futureEventsList;
    }

    public void setFutureEventsList(List<Event> futureEventsList){
        this.futureEventsList = futureEventsList;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public List<User> getFriendRequestsList(){
        return friendRequests;
    }

    public void setFriendRequestsList(List<User> friendRequests){
        this.friendRequests = friendRequests;
    }
}
