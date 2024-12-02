package com.holleryo.app;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vimal on 2014-07-31.
 */
public class HollerList {

    static List<Holler> hollers;

    public static List<Holler> getHollers() {

        hollers = new ArrayList<Holler>();

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        ParseQuery<ParseObject> hollerQuery = ParseQuery.getQuery("Holler");
        hollerQuery.whereMatchesQuery("user", userQuery);

        hollerQuery.include("user");
        hollerQuery.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> userList, ParseException e) {

                if (e == null) {

                    //loop through results
                    for (ParseObject dealsObject : userList) {

                        //get user table object for this record
                        ParseObject user = dealsObject.getParseObject("user");
                        String nameOfCreater = "";
                        String facebookId = "";


                        //get name of holler creater
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        JSONObject json = new JSONObject();
                        json = currentUser.getJSONObject("profile");
                        try {
                            nameOfCreater = json.getString("firstName") + " " + json.getString("lastName");
                            facebookId = json.getString("facebookId");
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }

                        //userProfilePictureView.setProfileId(facebookId);
                        //userNameView.setText(nameOfCreater);

                        //hollers.add(new Holler(dealsObject.getString("holler"),nameOfCreater, dealsObject.getCreatedAt().toString()));
                        //hollers.add(new Holler("This is a Holler", nameOfCreater, dealsObject.getCreatedAt().toString()));
                        //System.out.println(dealsObject.getString("holler"));

                    }


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return hollers;

    }
}
