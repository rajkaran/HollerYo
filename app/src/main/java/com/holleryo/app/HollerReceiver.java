package com.holleryo.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class HollerReceiver extends BroadcastReceiver {

    private static final String TAG = "ReceivedHoller";

    public HollerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent == null) {
                Log.d(TAG, "Receiver intent null");
            } else {
                String action = intent.getAction();
                Log.d(TAG, "got action " + action);
                if (action.equals("com.holleryo.app.UPDATE_STATUS")) {
                    Log.d(TAG, "got action " + action + " on channel " + "raj" + " with:");
                    JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                    Iterator itr = json.keys();
                    while (itr.hasNext()) {

                        String key = (String) itr.next();
                        if (key.equals("customdata")) {
                            Intent pupInt = new Intent(context, HollerViewActivity.class);
                            pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.getApplicationContext().startActivity(pupInt);
                        }
                        Log.d(TAG, "..." + key + " => " + json.getString(key));
                    }
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }


    }
}
