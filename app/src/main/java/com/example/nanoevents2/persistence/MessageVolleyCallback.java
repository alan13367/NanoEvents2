package com.example.nanoevents2.persistence;

public interface MessageVolleyCallback {
    void onSuccess(String response,Object o);
    default void onFailure(){

    }
}
