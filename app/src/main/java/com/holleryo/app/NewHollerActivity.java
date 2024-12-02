package com.holleryo.app;

import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

public class NewHollerActivity extends Activity {

    private Button createHoller;
    private EditText holler, descriptionEditText;

    private CheckBox chkDance, chkSports, chkPolitics, chkFashion;
    MultiSelectionSpinner spinner;
    Spinner rangeSpinner;
    Helper helperTool;
    Notification hollerNotification;
    ParseObject hollerObject;
    protected ProgressDialog proDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_holler);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        holler = (EditText) findViewById(R.id.hollerEditText);

        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        createHoller = (Button) findViewById(R.id.createHoller);


        //Assign string array to Spinner
        String[] array = getResources().getStringArray(R.array.dropDownInterest);
        spinner = (MultiSelectionSpinner) findViewById(R.id.categorySpinner);
        spinner.setItems(array);

        rangeSpinner = (Spinner) findViewById(R.id.rangeSpinner);
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.range_array,
                android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeSpinner.setAdapter(sAdapter);

        createHoller.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final Holler hollerObject = new Holler();

                final String rangeSpinnerVal = rangeSpinner.getSelectedItem().toString();

                hollerObject.put("user", ParseUser.getCurrentUser());
                hollerObject.put("username", ParseUser.getCurrentUser().getUsername());
                hollerObject.put("holler", holler.getText().toString());
                hollerObject.put("distance", rangeSpinnerVal);
                hollerObject.put("description", descriptionEditText.getText().toString());

                String selectedCategory = spinner.getSelectedItemsAsString();

                helperTool = new Helper();
                final JSONArray categoryArray = helperTool.convertStringToJSONArray(selectedCategory);

                hollerObject.put("interest", categoryArray);

                try {

                    startLoading();

                    hollerObject.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                // Saved successfully.
                                try{
                                    com.holleryo.app.Notification hollerNotify = new com.holleryo.app.Notification();
                                    hollerNotify.NotifyHoller(holler.getText().toString(), categoryArray, rangeSpinnerVal, hollerObject.getObjectId());
                                }
                                catch (Exception pE)
                                {
                                    Log.d("Notification Error", pE.toString());
                                }

                            } else {
                                // The save failed.
                                Log.d("failure msg", "holler post error: " + e);
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("failure msg", "holler post error: " + e);
                }
                stopLoading();
                finish();

                Intent intent = new Intent(NewHollerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void startLoading() {
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("posting...");
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    protected void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
    }
}

