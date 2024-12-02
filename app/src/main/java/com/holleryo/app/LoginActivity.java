package com.holleryo.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.CountCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

    private Button loginButton;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if there is a currently logged in user
        // and they are linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            getSession();
            startMainActivity();

        } else {

            setContentView(R.layout.activity_login);

            loginButton = (Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLoginButtonClicked();
                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void onLoginButtonClicked() {
        LoginActivity.this.progressDialog = ProgressDialog.show(
                LoginActivity.this, "", "Logging in...", true);

        List<String> permissions = Arrays.asList("public_profile", "user_about_me",
                "user_relationships", "user_birthday", "user_location");

        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException err) {

                LoginActivity.this.progressDialog.dismiss();
                if (user == null) {
                    Log.d(InitApplication.TAG,
                            "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(InitApplication.TAG,
                            "User signed up and logged in through Facebook!");
                    getSession();
                } else {
                    Log.d(InitApplication.TAG,
                            "User logged in through Facebook!");
                    getSession();
                }
            }
        });
    }

    private void getSession() {
        // Fetch Facebook user info if the session is active
        Session session = ParseFacebookUtils.getSession();
        if (session != null && session.isOpened()) {
            storeFacebookInfoInDatabase();
        }
    }

    private void storeFacebookInfoInDatabase() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
            @Override
            public void onCompleted(final GraphUser user, Response response) {
                if (user != null) {

                    // Create a JSON object to hold the profile info

                    ParseUser.getCurrentUser().setUsername(user.getName());


                    final JSONObject userProfile = new JSONObject();

                    try {
                        // Populate the JSON object
                        userProfile.put("facebookId", user.getId());
                        userProfile.put("name", user.getName());

                        if (user.getProperty("gender") != null) {
                            userProfile.put("gender", (String) user.getProperty("gender"));
                        } else {
                            userProfile.put("gender", "");
                        }

                        // Save the user profile info in a user property
                        ParseUser currentUser = ParseUser.getCurrentUser();

                        currentUser.put("profile", userProfile);

                        currentUser.saveInBackground();

                        // check if current user is already registered
                        ParseQuery userQuery = ParseUser.getQuery();
                        userQuery.whereEqualTo("fbId", user.getId());
                        userQuery.countInBackground(new CountCallback() {
                            public void done(int count, ParseException e) {

                                if (e == null) {
                                    if (count > 0) {
                                        Log.d(InitApplication.TAG, "Go to the holler count");
                                        // Save the user profile info in a user table
                                        ParseUser currentUser = ParseUser.getCurrentUser();
                                        currentUser.put("fbId", user.getId());
                                        currentUser.put("profile", userProfile);
                                        currentUser.saveInBackground();

                                        startMainActivity();
                                    } else {
                                        Log.d(InitApplication.TAG, "Show preferences");
                                        // Save the user profile info in a user table
                                        ParseUser currentUser = ParseUser.getCurrentUser();
                                        currentUser.put("fbId", user.getId());
                                        currentUser.put("profile", userProfile);
                                        currentUser.saveInBackground();
                                        startMainActivity();
                                    }
                                    // The count request succeeded. Log the count
                                    //Log.d("score", "Sean has played " + count + " games");
                                }
                            }
                        });

                        // save installation info in installation table
                        createInstallationForCurrentUser();
                    } catch (JSONException e) {
                        Log.d(InitApplication.TAG, "Error parsing returned user data.");
                    }

                } else if (response.getError() != null) {
                    if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
                            || (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
                        Log.d(InitApplication.TAG, "The facebook session was invalidated.");
                        //onLogoutButtonClicked();
                    } else {
                        Log.d(InitApplication.TAG, "Some other error: " + response.getError().getErrorMessage());
                    }
                }
            }
        });
        request.executeAsync();
    }

    private void createInstallationForCurrentUser() {
        // Associate the device with a user
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());

        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    e.printStackTrace();

                    Toast toast = Toast.makeText(getApplicationContext(), "Can't save data.", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // Check if the user is currently logged
            // and show any cached content
            Log.d(InitApplication.TAG, "resuming app with logged in user");

        } else {
            // If the user is not logged in, go to the
            // activity showing the login view.
            Log.d(InitApplication.TAG, "user is not logged in and we are resuming");
        }
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
