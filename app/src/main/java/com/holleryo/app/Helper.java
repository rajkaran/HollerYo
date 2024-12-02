package com.holleryo.app;

/**
 * Created by vimal on 2014-08-05.
 */

import org.json.JSONArray;

public class Helper {

    //input comma separated string and will return JSONArray
    public JSONArray convertStringToJSONArray(String CSVString) {

        JSONArray resultantJSONArray = new JSONArray();
        String[] inputStringIntoArray = CSVString.split(",");

        for (int i = 0; i < inputStringIntoArray.length; i++) {
            resultantJSONArray.put(inputStringIntoArray[i]);
        }

        return resultantJSONArray;
    }


}

