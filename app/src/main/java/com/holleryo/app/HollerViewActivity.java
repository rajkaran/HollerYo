package com.holleryo.app;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HollerViewActivity extends ListActivity {

    ActionBar actionBar;

    private TextView hollerTextView;
    private TextView userTextView;
    private TextView hollerDateTextView;
    private TextView hollerDescriptionTextView;

    private String userComment;
    private EditText userCommentEditText;
    Message messageList;
    String id;
    private RelatedCommentAdapter relatedCommentAdapter;
    protected ProgressDialog proDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holler_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("objectId");

        userCommentEditText = (EditText) findViewById(R.id.userComment);


        final ImageView postMessageButton = (ImageView) findViewById(R.id.postMessage);

        hollerTextView = (TextView) findViewById(R.id.holler);
        userTextView = (TextView) findViewById(R.id.user);
        hollerDateTextView = (TextView) findViewById(R.id.hollerDate);
        hollerDescriptionTextView = (TextView) findViewById(R.id.hollerDescription);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Holler");
        query.include("user");

        startLoading();

        query.getInBackground(id, new GetCallback<ParseObject>() {
            public void done(ParseObject hollerObject, ParseException e) {
                if (e == null) {

                    hollerTextView.setText(hollerObject.getString("holler"));
                    userTextView.setText(hollerObject.getString("username"));

                    Date createdAtDate = hollerObject.getCreatedAt();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd hh:mm a");
                    hollerDateTextView.setText(dateFormat.format(createdAtDate));
                    hollerDescriptionTextView.setText(hollerObject.getString("description"));

                    stopLoading();

                } else {
                    Log.d("Holler object error", "no holler");
                }
            }
        });


        postMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userComment = userCommentEditText.getText().toString();

                Comment userCommentObject = new Comment();
                userCommentObject.put("hollerId", id);
                userCommentObject.put("comment", userComment);
                userCommentObject.put("user", ParseUser.getCurrentUser());
                userCommentObject.put("username", ParseUser.getCurrentUser().getUsername());



                userCommentObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Saved successfully.
                            Log.d("success msg", "Message posted");
                            finish();
                            startActivity(getIntent());
                        } else {
                            Log.d("error msg", "Message not posted");
                        }

                    }

                });
            }
        });

        relatedCommentAdapter = new RelatedCommentAdapter(this, id);
        setListAdapter(relatedCommentAdapter);



        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_preference) {
            invokePreference();
            return true;
        }
        if (id == R.id.action_new_holler) {
            invokeNewHoller();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void invokeNewHoller() {
        Intent newHollerIntent = new Intent(this, NewHollerActivity.class);
        startActivity(newHollerIntent);
    }

    private void invokePreference() {
        Intent preferenceIntent = new Intent(this, MainPreferenceActivity.class);
        startActivity(preferenceIntent);
    }

    protected void startLoading() {
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("loading...");
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    protected void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
    }


}
