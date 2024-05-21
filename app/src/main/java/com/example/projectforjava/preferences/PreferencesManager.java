package com.example.projectforjava.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PreferencesManager {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_NAME = "profileName";
    private static final String KEY_PROFILE_ICON = "profileIcon";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Метод для сохранения строки
    public void setProfileName(String profileName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROFILE_NAME, profileName);
        editor.apply();
    }

    // Метод для получения строки
    public String getProfileName() {
        return sharedPreferences.getString(KEY_PROFILE_NAME, null);
    }

    // Метод для сохранения Bitmap
    public void setProfileIcon(Bitmap profileIcon) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROFILE_ICON, bitmapToBase64(profileIcon));
        editor.apply();
    }

    // Метод для получения Bitmap
    public Bitmap getProfileIcon() {
        String encodedImage = sharedPreferences.getString(KEY_PROFILE_ICON, null);
        if (encodedImage != null) {
            return base64ToBitmap(encodedImage);
        }
        return null;
    }

    // Метод для преобразования Bitmap в строку Base64
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Метод для преобразования строки Base64 в Bitmap
    private Bitmap base64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}