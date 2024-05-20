package com.example.projectforjava.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePreferences {

    private static final String PREF_NAME = "DatePreferences";
    private static final String KEY_LAST_LOGIN_DATE = "lastLoginDate";
    private static final String KEY_CALENDAR_TIME = "calendar_time";
    private static final String KEY_LAST_NOTIFICATION_HOUR = "last_notification_hour";

    public static void setLastLoginDate(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        editor.putString(KEY_LAST_LOGIN_DATE, currentDate);
        editor.apply();
    }

    public static boolean isFirstLoginToday(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastLoginDate = preferences.getString(KEY_LAST_LOGIN_DATE, "");
        String currentDate = sdf.format(new Date());
        return !lastLoginDate.equals(currentDate);
    }

    // Сохраняем время календаря и последний час уведомления в SharedPreferences
    public static void saveCalendarData(Context context, Calendar calendar, int lastNotificationHour) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long timeInMillis = calendar.getTimeInMillis();
        editor.putLong(KEY_CALENDAR_TIME, timeInMillis);
        editor.putInt(KEY_LAST_NOTIFICATION_HOUR, lastNotificationHour);
        editor.apply();
    }

    // Загружаем время календаря из SharedPreferences
    public static Calendar loadCalendarTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long timeInMillis = sharedPreferences.getLong(KEY_CALENDAR_TIME, 0);
        if(timeInMillis == 0)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    // Загружаем последний час уведомления из SharedPreferences
    public static int loadLastNotificationHour(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_LAST_NOTIFICATION_HOUR, -1);
    }
}

