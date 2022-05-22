package com.example.nanoevents2.model.entities.user;

public class User {
    private int id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;

    public static final int ACCEPT_REQUEST = 2;
    public static final int CREATE_REQUEST = 1;
    public static final int REJECT_REQUEST = 3;

    public User(int id,String name, String last_name, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String name,String image){
        this.name = name;
        this.image = image;
    }

    public User(int id,String name, String last_name, String email, String image) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
