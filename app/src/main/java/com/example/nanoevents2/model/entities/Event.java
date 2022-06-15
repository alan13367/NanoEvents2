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

    public static final int ALL_EVENTS = 0;
    public static final int ONGOING_EVENT = 1;
    public static final int FUTURE_EVENT = 2;
    public static final int FINISHED_EVENT = 3;

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

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getLocation() {
        return location;
    }

    public String getEventStart_date() {
        return eventStart_date;
    }

    public String getEventEnd_date() {
        return eventEnd_date;
    }

    public int getN_participators() {
        return n_participators;
    }

    public String getType() {
        return type;
    }
}
