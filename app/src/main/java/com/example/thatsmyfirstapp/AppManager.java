package com.example.thatsmyfirstapp;

import android.app.Activity;
import android.content.Context;

import com.example.thatsmyfirstapp.data.model.UserModel;


public class AppManager {
    public static Context context;
    public static String userId;
    public static UserModel userModel;
    public static int appRequestAttempt = 0;
    private static Activity activity;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppManager.context = context;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        AppManager.activity = activity;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        AppManager.userId = userId;
    }

    public static UserModel getUserModel() {
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        AppManager.userModel = userModel;
    }
}
