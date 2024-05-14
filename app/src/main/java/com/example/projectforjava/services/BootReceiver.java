package com.example.projectforjava.services;

import static com.example.projectforjava.services.Notificator.updateDatabase;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.projectforjava.preferences.DatePreferences;

import java.util.Calendar;
import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            // Проверяем, был ли уже сегодня первый вход
            if (DatePreferences.isFirstLoginToday(context)) {
                DatePreferences.setLastLoginDate(context);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        updateDatabase(context);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Notificator.scheduleNotification(context.getApplicationContext(), Calendar.getInstance());
                    }
                }.execute();
            }
            else {
                Notificator.scheduleNotification(context.getApplicationContext(), Calendar.getInstance());
            }
        }
    }
}
