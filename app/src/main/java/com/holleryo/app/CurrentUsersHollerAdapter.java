package com.holleryo.app;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vimal on 2014-08-05.
 */
public class CurrentUsersHollerAdapter extends ParseQueryAdapter<Holler> {

    ParseUser user;
    private ProfilePictureView userProfilePictureView;

    public CurrentUsersHollerAdapter(Context context) {

        super(context, new QueryFactory<Holler>() {

            public ParseQuery<Holler> create() {

                ParseUser cUser = ParseUser.getCurrentUser();

                ParseQuery query = new ParseQuery("Holler");
                query.whereEqualTo("user", cUser);
                query.include("user");

                return query;
            }
        });
    }

    @Override
    public View getItemView(final Holler holler, View v, ViewGroup parent) {

        user = holler.getUser();

        if (v == null) {
            v = View.inflate(getContext(), R.layout.holler_item, null);
        }

        super.getItemView(holler, v, parent);

        userProfilePictureView = (ProfilePictureView) v.findViewById(R.id.userFBImg);

        TextView txtHoller = (TextView) v.findViewById(R.id.holler);
        txtHoller.setText(holler.getHoller());


        TextView txtUser = (TextView) v.findViewById(R.id.user);
        txtUser.setText(holler.getUserName());

        TextView createdAt = (TextView) v.findViewById(R.id.createdAt);

        Date createdAtDate = holler.getCreatedAt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd hh:mm a");
        createdAt.setText(dateFormat.format(createdAtDate));

        JSONObject userProfile = user.getJSONObject("profile");

        if (userProfile != null) {
            String facebookId = getFacebookId(userProfile);


            try {
                userProfilePictureView.setProfileId(facebookId);
                Log.d("Facebook Id", facebookId);
            } catch (Exception e) {
                Log.d("Profile pic error", e.toString());
            }

        }


        return v;
    }

    public String getFacebookId(JSONObject userProfile) {
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
