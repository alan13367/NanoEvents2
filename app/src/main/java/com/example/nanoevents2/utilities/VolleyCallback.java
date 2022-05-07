package com.example.nanoevents2.utilities;

public interface VolleyCallback {
    void onSuccess();
    default void onFailure() {

    }
}
