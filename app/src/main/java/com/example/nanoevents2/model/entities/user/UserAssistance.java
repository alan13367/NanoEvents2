package com.example.nanoevents2.model.entities.user;

public class UserAssistance {
    private int id;
    private String name;
    private String last_name;
    private String email;
    private int puntuation;
    private String comentary;
    private int eventId;

    public UserAssistance(int id, String name, String last_name, String email, int puntuation, String comentary, int eventId) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.puntuation = puntuation;
        this.comentary = comentary;
        this.eventId = eventId;
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

    public int getPuntuation() {
        return puntuation;
    }

    public String getComentary() {
        return comentary;
    }

    public int getEventId() {
        return eventId;
    }
}
