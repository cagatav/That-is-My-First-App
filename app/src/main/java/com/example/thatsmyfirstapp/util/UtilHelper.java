package com.example.thatsmyfirstapp.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class UtilHelper {

    public  static boolean ValidateData(String email, String password, EditText editText) {
        if (email.isEmpty()){
            editText.setError("Email is required!");
            return false;
        }
        if (password.isEmpty()){
            editText.setError("Password is required!");
            return false;
        }
        if (password.length() < 6) {
            editText.setError("Password is too short.");
            return false;
        }
        return true;
    }

    public static boolean checkNetwork(Context context) {
        try {
            ConnectivityManager connectionService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectionService.getActiveNetworkInfo();
            if (info != null)
                return info.isConnected();
            else
                return false;
        } catch (NullPointerException e) {
            return false;
        }
    }


    public static AudioManager getAudioManager(Context context) {
        return (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null)
            view = new View(activity);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm = null;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static String coinStringFormat(String value) {
        while (value.endsWith("0")) {
            value = removeLastChars(value, 1);
        }
        return value;
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}
