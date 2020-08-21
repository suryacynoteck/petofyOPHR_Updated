package com.cynoteck.petofyvet.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Config {


    public static String token="";
    public static String user_id="";
    public static String type="";


    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
}
