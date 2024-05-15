package com.example.projectforjava.services;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.MainActivity;
import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.db.PersonalChallengeDatabase;
import com.example.projectforjava.database.model.PersonalChallenge;
import com.example.projectforjava.preferences.DatePreferences;
import com.example.projectforjava.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Notificator {
    @SuppressLint("ScheduleExactAlarm")
    public static void scheduleNotification(Context context, @Nullable Calendar newCalendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }

        Calendar calendar = DatePreferences.loadCalendarTime(context);
        int lastNotificationHour = DatePreferences.loadLastNotificationHour(context);

        if(calendar == null || calendar.getTimeInMillis() == 0){
            calendar = Calendar.getInstance();
        } else if (newCalendar != null) {
            if(newCalendar.getTimeInMillis() > calendar.getTimeInMillis()){
                calendar = newCalendar;
                lastNotificationHour = -1;
            }
            else{
                return;
            }
        }

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if(lastNotificationHour == -1){
            int[] notificationHours = {4, 8, 12, 16, 20, 24};
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int hour : notificationHours) {
                if (hour > currentHour) {
                    lastNotificationHour = hour % 24;
                    break;
                }
            }
        }
        else{
            lastNotificationHour = (lastNotificationHour + 4) % 24;
        }

        if(lastNotificationHour == 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, lastNotificationHour);

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        DatePreferences.saveCalendarData(context, calendar, lastNotificationHour);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @SuppressLint({"MissingPermission"})
    public static void sendNotification(Context context) {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return getUncompletedChallengeTitles(context);
            }

            @Override
            protected void onPostExecute(List<String> uncompletedChallengeTitles) {
                super.onPostExecute(uncompletedChallengeTitles);
                if(!uncompletedChallengeTitles.isEmpty()){
                    createNotificationChannel(context);

                    // Создаем Intent для открытия MainActivity при нажатии на уведомление
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Невыполненные челленджи")
                            .setContentText("Сегодня вам осталось выполнить следующие челленджи: " + String.join(", ", uncompletedChallengeTitles) + "!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent) // Устанавливаем PendingIntent
                            .setAutoCancel(true); // Уведомление будет автоматически удаляться при нажатии

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
                }
            }
        }.execute();
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(MainActivity.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static class NotificationReceiver extends BroadcastReceiver {
        @SuppressLint("StaticFieldLeak")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isAppInForeground(context)) {                    // Отправить уведомление, если приложение не активно
                if(DatePreferences.loadLastNotificationHour(context) == 0){
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            updateDatabase(context);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            sendNotification(context);
                        }
                    }.execute();
                }
                else {
                    sendNotification(context);
                }
            }
            // Переустанавливаем следующее уведомление (если нужно)
            scheduleNotification(context, null);
        }
    }

    public static boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        if (runningProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    return processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                }
            }
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    public static void updateDatabase(Context context) {
        // Получаем экземпляр базы данных
        PersonalChallengeDatabase personalChallengeDatabase = PersonalChallengeDatabase.getInstance(context);

        // Получаем экземпляр дао
        PersonalChallengeDao personalChallengeDao = personalChallengeDatabase.challengeDao();

        // Получаем все челленджи из базы данных
        List<PersonalChallenge> personalChallenges = personalChallengeDao.getAllChallenges();

        // Обновляем значение isCompletedToday для каждого челленджа
        for (PersonalChallenge personalChallenge : personalChallenges) {
            personalChallenge.setCompletedToday(false);
            personalChallengeDao.update(personalChallenge);
        }
    }

    public static List<String> getUncompletedChallengeTitles(Context context) {
        PersonalChallengeDatabase personalChallengeDatabase = PersonalChallengeDatabase.getInstance(context);
        PersonalChallengeDao personalChallengeDao = personalChallengeDatabase.challengeDao();
        List<String> uncompletedChallengeTitles = personalChallengeDao.getUncomletedChallengeTitles(false);
        String currentDayOfWeek = DateUtil.getCurrentDayOfWeek();

        Iterator<String> iterator = uncompletedChallengeTitles.iterator();
        while (iterator.hasNext()) {
            String title = iterator.next();
            PersonalChallenge personalChallenge = personalChallengeDao.getChallengeByTitle(title);
            if (!personalChallenge.getDays().contains(currentDayOfWeek)) {
                iterator.remove();
            }
        }

        return uncompletedChallengeTitles;
    }
}
