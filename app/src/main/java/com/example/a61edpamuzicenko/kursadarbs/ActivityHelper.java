package com.example.a61edpamuzicenko.kursadarbs;

import android.app.Activity;
import android.content.pm.ActivityInfo;

public class ActivityHelper {
    public static void initialize(Activity activity) {
        //Delaet orientaciju ekrana portretom
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}