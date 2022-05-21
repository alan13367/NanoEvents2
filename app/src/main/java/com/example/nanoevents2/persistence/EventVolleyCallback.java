package com.example.nanoevents2.persistence;

import com.example.nanoevents2.model.entities.Event;

import java.util.ArrayList;

public interface EventVolleyCallback {
    void onSuccess(String response,Object o);
    default void onFailure() {
    }
}
