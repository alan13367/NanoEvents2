package com.example.nanoevents2.persistence;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nanoevents2.model.utilities.JsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyAPISingleton {
    private static MyAPISingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;
    private static String accessToken;
    public final String baseurl = "http://puigmal.salle.url.edu/api/v2/";
    public static final String login_url = "http://puigmal.salle.url.edu/api/v2/users/login";
    public static final String users_base_url = "http://puigmal.salle.url.edu/api/v2/users";
    public static final String searchUser = "http://puigmal.salle.url.edu/api/v2/users/search?s=";
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


    public static void login(Context context,String email,String password,final UserVolleyCallback userVolleyCallback){
        if(!email.isEmpty() && !password.isEmpty()){
            JSONObject body = new JsonBuilder().add("email",email).add("password",password).json;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,login_url
                    ,body, response ->{
                        try {
                            accessToken = response.getString("accessToken");
                            userVolleyCallback.onSuccess(accessToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error ->{
                error.printStackTrace();
                userVolleyCallback.onFailure();
            });

            getInstance(context).addToRequestQueue(jsonObjectRequest);
        }else {
            userVolleyCallback.onFailure();
        }

    }

    public static void signUp(Context context,String name, String last_name, String email, String password, String imagePath, final UserVolleyCallback userVolleyCallback){
        JSONObject body = new JsonBuilder().add("name",name).add("last_name",last_name).add("email",email).add("password",password).add("image",imagePath).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,users_base_url
                ,body, response ->{
                    userVolleyCallback.onSuccess(response.toString());
                }, error ->{
            userVolleyCallback.onFailure();
        });

        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUser(Context context,String email, final UserVolleyCallback userVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                searchUser+email,null,
                response -> {
                    try {
                        JSONObject user = response.getJSONObject(0);
                        userVolleyCallback.onSuccess(user.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> userVolleyCallback.onFailure()) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }

        };
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void getUserStatistics(Context context,int userId,final UserVolleyCallback userVolleyCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                users_base_url+"/"+userId+"/statistics",null,
                response -> {
                        userVolleyCallback.onSuccess(response.toString());
                }, error -> {
            userVolleyCallback.onFailure();
        }){
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }

        };
        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void editUserParameters(Context context,JSONObject requestBody,final UserVolleyCallback userVolleyCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                users_base_url,requestBody,
                response -> {
                    userVolleyCallback.onSuccess(response.toString());
                }, error -> {
            userVolleyCallback.onFailure();
        }){
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }

        };
        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void deleteUser(Context context, final UserVolleyCallback userVolleyCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                users_base_url,null,
                response -> {
                    userVolleyCallback.onSuccess("OK");
                }, error -> {
            userVolleyCallback.onFailure();
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }

        };
        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
