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
 * Created by vimal on 2014-08-06.
 */


public class RelatedCommentAdapter extends ParseQueryAdapter<Comment> {

    ParseUser user;
    private ProfilePictureView userProfilePictureView;


    public RelatedCommentAdapter(Context context, final String hollerId) {

        super(context, new ParseQueryAdapter.QueryFactory<Comment>() {

            @Override
            public ParseQuery<Comment> create() {
                ParseQuery query = new ParseQuery("Comment");
                query.whereEqualTo("hollerId", hollerId);
                query.include("user");

                return query;
            }
        });
    }

    @Override
    public View getItemView(Comment comment, View v, ViewGroup parent) {

        user = comment.getUser();

        if (v == null) {
            v = View.inflate(getContext(), R.layout.message_item, null);
        }

        super.getItemView(comment, v, parent);

        userProfilePictureView = (ProfilePictureView) v.findViewById(R.id.userFBImg);

        TextView userTextView = (TextView) v.findViewById(R.id.user);
        TextView createdAtTextView = (TextView) v.findViewById(R.id.createdAt);
        TextView hollerMessageTextView = (TextView) v.findViewById(R.id.hollerMessage);

        userTextView.setText(comment.getUserName());

        Date createdAtDate = comment.getCreatedAt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd hh:mm a");
        createdAtTextView.setText(dateFormat.format(createdAtDate));

        hollerMessageTextView.setText(comment.getComment());

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
