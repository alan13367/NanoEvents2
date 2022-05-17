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
import com.example.nanoevents2.model.entities.Event;
import com.example.nanoevents2.model.entities.user.User;
import com.example.nanoevents2.model.entities.user.UserStatistics;
import com.example.nanoevents2.model.utilities.JsonBuilder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyAPISingleton {
    private static MyAPISingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;
    private static String accessToken;
    public final String baseurl = "http://puigmal.salle.url.edu/api/v2/";
    //Users
    public static final String login_url = "http://puigmal.salle.url.edu/api/v2/users/login";
    public static final String users_base_url = "http://puigmal.salle.url.edu/api/v2/users";
    public static final String searchUser = "http://puigmal.salle.url.edu/api/v2/users/search?s=";

    //Events
    public static final String events_base_url = "http://puigmal.salle.url.edu/api/v2/events";
    public static final String best_events_url = "http://puigmal.salle.url.edu/api/v2/events/best";
    public static final String searchEventsUrl="http://puigmal.salle.url.edu/api/v2/events/search?";

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
                            userVolleyCallback.onSuccess(accessToken,null);
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

    public static void signUp(Context context,String name, String last_name, String email
            , String password, String imagePath, final UserVolleyCallback userVolleyCallback){
        JSONObject body = new JsonBuilder().add("name",name).add("last_name",last_name).add("email",email).add("password",password).add("image",imagePath).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,users_base_url
                ,body, response ->{
                    userVolleyCallback.onSuccess(response.toString(),null);
                }, error ->{
            userVolleyCallback.onFailure();
        });

        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUserByEmail(Context context, String email, final UserVolleyCallback userVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                searchUser+email,null,
                response -> {
                    try {
                        JSONObject user = response.getJSONObject(0);
                        userVolleyCallback.onSuccess(user.toString()
                                ,new Gson().fromJson(user.toString(), User.class));
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
                        userVolleyCallback.onSuccess(response.toString()
                                ,new Gson().fromJson(response.toString(), UserStatistics.class));
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

    public static void editUserParameters(Context context,JSONObject parametersToModify,final UserVolleyCallback userVolleyCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                users_base_url,parametersToModify,
                response -> {
                    userVolleyCallback.onSuccess(response.toString(),null);
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
                    userVolleyCallback.onSuccess("OK",null);
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

    public static void getEventsFromUser(Context context,int userId,int eventStatus,final EventsVolleyCallback eventsVolleyCallback){
        String url = users_base_url+"/"+userId+"/events";
        switch(eventStatus){
            case Event.ALL_EVENTS:
                url = users_base_url+"/"+userId+"/events";
                break;
            case Event.FUTURE_EVENT:
                url = users_base_url+"/"+userId+"/events/future";
                break;
            case Event.ONGOING_EVENT:
                url = users_base_url+"/"+userId+"/events/current";
                break;
            case Event.FINISHED_EVENT:
                url = users_base_url+"/"+userId+"/events/finished";
                break;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,null,
                response -> {
                    Event[] eventArray = new Gson().fromJson(response.toString(), (Type) Event[].class);
                    eventsVolleyCallback.onSuccess(response.toString(),new ArrayList<>(Arrays.asList(eventArray)));
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void getUserEventsAssistance(Context context, int userId, int eventStatus
            , final EventsVolleyCallback eventsVolleyCallback){
        String url = users_base_url+"/"+userId+"/assistances";
        switch(eventStatus){
            case Event.ALL_EVENTS:
                url = users_base_url+"/"+userId+"/assistances";
                break;
            case Event.FUTURE_EVENT:
                url = users_base_url+"/"+userId+"assistances/future";
                break;
            case Event.FINISHED_EVENT:
                url = users_base_url+"/"+userId+"assistances/finished";
                break;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,null,
                response -> {
                    Event[] eventArray = new Gson().fromJson(response.toString(), (Type) Event[].class);
                    eventsVolleyCallback.onSuccess(response.toString(),new ArrayList<>(Arrays.asList(eventArray)));
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void getUserFriends(Context context,int userId,final UserVolleyCallback userVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                users_base_url+"/"+userId+"/friends",null,
                response -> {
                    try {
                        ArrayList<User> users = new ArrayList<>();
                        for(int i = 0;i<response.length();i++){
                            JSONObject o = response.getJSONObject(i);
                            users.add(new User(o.getInt("id"),o.getString("name")
                                    ,o.getString("last_name"),o.getString("email")
                                    ,o.getString("image")));

                        }
                        userVolleyCallback.onSuccess(response.toString(),users);
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

    public static void createEvent(Context context,String name,String imageUrl,String location
            ,String description,String startDate,String endDate,int numParticipants,String type
            ,final EventsVolleyCallback eventsVolleyCallback){

        JSONObject body = new JsonBuilder()
                .add("name",name)
                .add("image",imageUrl)
                .add("location",location)
                .add("description",description)
                .add("eventStart_date",startDate)
                .add("eventEnd_date",endDate)
                .add("n_participators",numParticipants)
                .add("type",type).json;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,events_base_url
                , body, response ->{
            int owner = 0;
            String date = null;
            try {
                owner = response.getInt("owner_id");
                date = response.getString("date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String finalDate = date;
            getEventsFromUser(context, owner, Event.ALL_EVENTS, new EventsVolleyCallback() {
                @Override
                public void onSuccess(String jsonResponse, Object o) {
                    ArrayList<Event> events= (ArrayList<Event>)o;
                    for (Event e:events){
                        if (e.getDate().equals(finalDate)){
                            eventsVolleyCallback.onSuccess("Event Created",e);
                        }
                    }
                }
            });
            eventsVolleyCallback.onSuccess(response.toString(),null);
        }, error ->{
            error.printStackTrace();
            eventsVolleyCallback.onFailure();
        });

        getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public static void getAllFutureEvents(Context context,final EventsVolleyCallback eventsVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                events_base_url,null,
                response -> {
                    Event[] eventArray = new Gson().fromJson(response.toString(), (Type) Event[].class);
                    eventsVolleyCallback.onSuccess(response.toString(),new ArrayList<>(Arrays.asList(eventArray)));
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void getEventById(Context context, int id, final EventsVolleyCallback eventsVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                events_base_url+"/"+id,null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        eventsVolleyCallback.onSuccess(response.toString()
                                ,new Gson().fromJson(jsonObject.toString(),Event.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void getBestEvents(Context context,final EventsVolleyCallback eventsVolleyCallback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                best_events_url,null,
                response -> {
                    Event[] eventArray = new Gson().fromJson(response.toString(), (Type) Event[].class);
                    eventsVolleyCallback.onSuccess(response.toString(),new ArrayList<>(Arrays.asList(eventArray)));
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void searchEvents(Context context,String keywordQuery,String locationQuery
            ,String dateQuery,final EventsVolleyCallback eventsVolleyCallback){
        String url = String.format("http://puigmal.salle.url.edu/api/v2/events/search?location=%1$s&date=%2$s&keyword=%3$s"
                ,locationQuery,dateQuery,keywordQuery);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,null,
                response -> {
                    Event[] eventArray = new Gson().fromJson(response.toString(), (Type) Event[].class);
                    eventsVolleyCallback.onSuccess(response.toString(),new ArrayList<>(Arrays.asList(eventArray)));
                }, error -> {
            eventsVolleyCallback.onFailure();
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
        getInstance(context).addToRequestQueue(jsonArrayRequest);
    }



}
