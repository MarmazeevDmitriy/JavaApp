package com.example.projectforjava.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthPreferencesManager {

    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_IS_LOGINED = "is_logined";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AuthPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Methods for isLogined
    public void setIsLogined(boolean isLogined) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGINED, isLogined);
        editor.apply();
    }

    public boolean getIsLogined() {
        return sharedPreferences.getBoolean(KEY_IS_LOGINED, false); // Default is false
    }

    // Methods for email
    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, ""); // Default is empty string
    }

    // Methods for password
    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, ""); // Default is empty string
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
