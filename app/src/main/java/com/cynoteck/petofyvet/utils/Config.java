package com.cynoteck.petofyvet.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Config {

    public  static  int count =1;
    public static String token="";
    public static String user_id="";
    public static String user_Veterian_name="";
    public static String user_Veterian_phone="";
    public static String user_Veterian_emial="";
    public static String type="";
    public static String backCall="";


    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
   public static String currentDate()
   {
       String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
       return date;
   }
}
