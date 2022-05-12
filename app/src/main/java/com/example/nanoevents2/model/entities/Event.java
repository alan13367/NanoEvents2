package com.example.nanoevents2.model.entities;

public class Event {
    private int id;
    private String name;
    private int owner_id;
    private String date;
    private String image;
    private String location;
    private String description;
    private String eventStart_date;
    private String eventEnd_date;
    private int n_participators;
    private String type;

    public Event(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
