package com.example.nanoevents2.persistence;

public interface UserVolleyCallback {
    void onSuccess(String response);
    default void onFailure() {
    }
}
