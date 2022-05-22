package com.example.nanoevents2.persistence;

import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private User user;
    private List<Event> futureEventsList;
    private List<User> friendRequests;
    private List<User> usersMyMessagesUsers;

    private DataManager(){
        futureEventsList = new ArrayList<>();
        friendRequests = new ArrayList<>();
        usersMyMessagesUsers = new ArrayList<>();
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

    public List<User> getUsersMyMessagesUsers() {
        return usersMyMessagesUsers;
    }

    public void setUsersMyMessagesUsers(List<User> usersMyMessagesUsers) {
        this.usersMyMessagesUsers = usersMyMessagesUsers;
    }
}
