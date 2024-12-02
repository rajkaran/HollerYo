package com.holleryo.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.PushService;

/**
 * Created by vimal on 2014-08-04.
 */
public class InitApplication extends Application {

    static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Holler.class);


        Parse.initialize(this, "1naQa1Pyi6sztD6e411DxHsrHQsRyMulBz1NOk1v", "Uiyibd30ed4tkdX2fwDSVR14gJLBlkIca4YTeyJn");

        // Set your Facebook App Id in strings.xml
        ParseFacebookUtils.initialize(getString(R.string.app_id));

        PushService.setDefaultPushCallback(this, HollerViewActivity.class);

    }
}
