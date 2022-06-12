package com.example.nanoevents2.persistence;

import android.graphics.Bitmap;

import com.example.nanoevents2.BuildConfig;
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private User user;
    private Bitmap userProfileImage;
    private Bitmap defaultProfileImage;
    private List<User> friends;
    private List<Event> futureEventsList;
    private List<User> friendRequests;
    private List<User> usersMyMessagesUsers;
    private List<Event> allUserEvents;

    private DataManager(){
        futureEventsList = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        usersMyMessagesUsers = new ArrayList<>();
        allUserEvents = new ArrayList<>();
    }


    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public Bitmap getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Bitmap userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public void setDefaultProfileImage(Bitmap defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    public Bitmap getDefaultProfileImage() {
        return defaultProfileImage;
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

    public void addUserMyMessages(User user){
        boolean exists = false;
        for(User u:usersMyMessagesUsers){
            if(u.getEmail().equals(user.getEmail())){
                exists = true;
                break;
            }
        }
        if(!exists){
            usersMyMessagesUsers.add(user);
        }
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Event> getAllUserEvents() {
        return allUserEvents;
    }

    public void setAllUserEvents(List<Event> allUserEvents) {
        this.allUserEvents = allUserEvents;
    }

    public static void clearDataManager(){
        instance = null;
    }
}