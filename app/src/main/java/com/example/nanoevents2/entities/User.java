package com.example.nanoevents2.entities;

public class User {
    private int id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;

    public User(int id,String name, String last_name, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }
}
