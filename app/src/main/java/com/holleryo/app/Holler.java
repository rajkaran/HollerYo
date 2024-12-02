package com.holleryo.app;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

/**
 * Holler
 * Created by vimal on 2014-07-31.
 */

@ParseClassName("Holler")
public class Holler extends ParseObject {


    public Holler() {
    }

    public String getHoller() {

        return getString("holler");
    }

    public void setHoller(String holler) {
        put("holler", holler);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public String getUserName() {
        return getString("username");
    }


    public String getCreatedDate() {
        return getCreatedAt().toString();
    }

    public JSONObject getProfile() {
        return getJSONObject("profile");
    }

    public String getFacebookId() {

        JSONObject userProfile = getProfile();
        String facebookId = "";

        try {
            if (userProfile.get("facebookId") != null) {
                facebookId = userProfile.get("facebookId")
                        .toString();

            } else {
                // Show the default, blank user profile picture
                facebookId = "";
            }
        } catch (Exception e) {
            Log.d("Profile pic error in function", e.toString());
        }

        return facebookId;
    }

}
