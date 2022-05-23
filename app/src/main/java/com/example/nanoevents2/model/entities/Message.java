package com.example.nanoevents2.model.entities;

public class Message {
    private int id;
    private String content;
    private int user_id_send;
    private int user_id_recived;
    private String timeStamp;


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getUser_id_send() {
        return user_id_send;
    }

    public int getUser_id_recived() {
        return user_id_recived;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
