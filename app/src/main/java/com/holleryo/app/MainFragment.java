package com.holleryo.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ProfilePictureView userProfilePictureView;
    private TextView welcomeMessage, hollerCount;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        userProfilePictureView = (ProfilePictureView) view.findViewById(R.id.userProfilePicture);

        welcomeMessage = (TextView) view.findViewById(R.id.welcome_message);
        hollerCount = (TextView) view.findViewById(R.id.holler_count);

        ParseUser currentUser = ParseUser.getCurrentUser();

        String welcomeMessageString = String.format(getResources().getString(R.string.welcome_message), currentUser.getUsername());
        welcomeMessage.setText(welcomeMessageString);

        if (currentUser.get("profile") != null) {
            JSONObject userProfile = currentUser.getJSONObject("profile");

            try {
                if (userProfile.getString("facebookId") != null) {
                    String facebookId = userProfile.get("facebookId")
                            .toString();
                    userProfilePictureView.setProfileId(facebookId);
                } else {
                    // Show the default, blank user profile picture
                    userProfilePictureView.setProfileId(null);
                }
            } catch (Exception e) {
                Log.d("Profile pic error", e.toString());
            }
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Holler");
        ParseUser cUser = ParseUser.getCurrentUser();
        query.whereNotEqualTo("user", cUser);
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    String message = "You have " + count + " new Hollers!";
                    hollerCount.setText(message);
                } else {
                    // The request failed
                }
            }
        });

        hollerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
                viewPager.setCurrentItem(1);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
