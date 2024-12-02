package com.holleryo.app;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Notification {

    private String holler;
    private String distanceString;
    private String hollerId;

    public Notification() {
    }

    public void NotifyHoller(String sHoller, JSONArray interestsArray, String distance, String sHollerId) {

        // Create our Installation query
        //ParseQuery pushQuery = ParseInstallation.getQuery();

        holler = sHoller;
        distanceString = distance;
        hollerId = sHollerId;

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        ParseQuery<ParseObject> location = ParseQuery.getQuery("UserPreference");
        //location.whereEqualTo("location", "Kitchener");
        location.whereMatchesQuery("user", userQuery);

        // Include fields of user table
        location.include("user");
        location.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    //loop through results
                    for (ParseObject dealsObject : userList) {

                        //get user table object for this record
                        ParseObject user = dealsObject.getParseObject("user");

                        //get name of holler creater
                        ParseUser currentUser = ParseUser.getCurrentUser();

                        String nameOfCreater = currentUser.getUsername();

                        // Find devices associated with these users to notify
                        ParseQuery pushQuery = ParseInstallation.getQuery();
                        pushQuery.whereEqualTo("user", user);

                        //set push notification variables
                        JSONObject data = new JSONObject();
                        try {
                            data.put("action", "com.holleryo.app.UPDATE_STATUS");
                            data.put("alert", holler);
                            data.put("title", nameOfCreater);//to show name in system tray
                            data.put("hollerId", hollerId);
                        } catch (JSONException e3) {
                            // TODO Auto-generated catch block
                            e3.printStackTrace();
                        }

                        // Send push notification to query
                        ParsePush push = new ParsePush();
                        push.setQuery(pushQuery);
                        push.setData(data);
                        push.sendInBackground();

                    }


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }


}
