package com.example.nanoevents2.model.utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonBuilder {
    public final JSONObject json = new JSONObject();

    public String toJson() {
        return json.toString();
    }

    public JsonBuilder add(String key, String value) {
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonBuilder add(String key, int value) {
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

}
