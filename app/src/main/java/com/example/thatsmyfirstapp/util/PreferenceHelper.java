/*
 * *
 *  * Created by Cihat Mert Baykal
 *  * Copyright (c) Ikol Mobile. All rights reserved.
 *  * Last modified 2/8/21 1:30 PM
 *
 */

package com.example.thatsmyfirstapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.thatsmyfirstapp.AppManager;


public final class PreferenceHelper {

    private static SharedPreferences getSharedPreferences() {
        return AppManager.getContext().getSharedPreferences("myApp", Context.MODE_PRIVATE);
    }

    public static void addInt(String prefKey, Integer value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(prefKey, value);
        editor.apply();
    }

    public static void addString(String prefKey, String prefValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefKey, prefValue);
        editor.apply();
    }

    public static void addBoolean(String prefKey, Boolean state) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(prefKey, state);
        editor.apply();
    }


    public static void addFloat(String prefKey, float value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(prefKey, value);
        editor.apply();
    }

    public static void addLong(String prefKey, Long value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(prefKey, value);
        editor.apply();
    }

    public static Integer getInt(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getInt(key, 0);
    }

    public static String getString(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(key, "");
    }

    public static Boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getBoolean(key, false);
    }


    public static float getFloat(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getFloat(key, 0);
    }

    public static Long getLong(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getLong(key, 0L);
    }

    public static void delete(String prefTag) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(prefTag);
        editor.apply();
    }

    public static void deletePreferences(String prefTag) {
        SharedPreferences sharedPreferences = AppManager.getContext().getSharedPreferences(prefTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
