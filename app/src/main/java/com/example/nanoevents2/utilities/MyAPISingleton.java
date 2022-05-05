package com.example.nanoevents2.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAPISingleton {
    private static MyAPISingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;
    private final String baseurl = "http://puigmal.salle.url.edu/api/v2/";
    private static final String users_url = "http://puigmal.salle.url.edu/api/v2/users";
    private static final String login_url = "http://puigmal.salle.url.edu/api/v2/users/login";

    private MyAPISingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MyAPISingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MyAPISingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    //Will return the access token for further requests or null if failed
    public JsonObjectRequest getAccessToken(String email,String password){
        JSONObject body = new JsonBuilder().add("email",email).add("password",password).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,login_url,body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        return jsonObjectRequest;
    }

    //Returns true or false wether it has failed to register the user or not
    public boolean signUp(String name,String last_name,String email,String password, String image){
        final boolean[] responseBool = new boolean[1];
        JSONObject body = new JsonBuilder().add("name",name).add("last_name",last_name)
                .add("email",email).add("password",password).add("image",image).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,users_url,body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseBool[0] = true;

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        addToRequestQueue(jsonObjectRequest);
        return responseBool[0];
    }
}
