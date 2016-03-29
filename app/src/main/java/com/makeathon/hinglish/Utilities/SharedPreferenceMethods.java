package com.makeathon.hinglish.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SharedPreferenceMethods
{
    public static Context appContext;
    private static String PREFERENCE="makeaThon_Hinglish";
    public static final String SHARED_PREFERENCE_NAME="hinglish_shared_preference";

    public static final String PLAYER_1_NAME = "player_1";
    public static final String PLAYER_2_NAME = "player_2";
    public static final String SINGLE_PLAYER_NAME = "single_player_name";
    public static final String SINGLE_PLAYER_BADGE = "single_player_badge";

    public static final String SOUND = "sound";

    /*
    public static final String GCM_REGISTER_ID ="gcm_registration_id";
    public static final String USER_ID = "user_id";
    public static final String USER_MOBILE_NO = "user_mobile_no";
    public static final String USER_TOKEN = "user_token";
    public static final String FIRST_TIME_LOGIN = "first_time_login";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DEFAULT_LATITUDE = "default_latitude";
    public static final String DEFAULT_LONGITUDE = "default_longitude";

    //fetching current lat long.
    public static final String CURRENT_LATITUDE = "current_latitude";
    public static final String CURRENT_LONGITUDE = "current_longitude";
    public static final String DISTANCE = "distance";

    */

    public static SharedPreferences.Editor getEditor(Context context)
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return editor;
    }
    public static SharedPreferences getSharedPreference(Context context)
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    public static boolean getBoolean(Context context , String name)
    {
        SharedPreferences sharedPreferences=getSharedPreference(context);
        return sharedPreferences.getBoolean(name,false);
    }
    public static void setBoolean(Context context , String name , boolean value)
    {
        SharedPreferences.Editor editor=getEditor(context);
        editor.putBoolean(name,value);
        editor.commit();
    }
    public static String getString(Context context , String name)
    {
        SharedPreferences sharedPreferences=getSharedPreference(context);
        return sharedPreferences.getString(name, "");
    }
    public static void setString(Context context , String name , String value)
    {
        SharedPreferences.Editor editor=getEditor(context);
        editor.putString(name, value);
        editor.commit();
    }

    // for username string preferences
    public static void setDoubleSharedPreference(Context context, String name,double value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(name, (float) value);
        editor.commit();
    }

    public static Double getDoubleSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        return (double)settings.getFloat (name, 0.0f);
    }

}
