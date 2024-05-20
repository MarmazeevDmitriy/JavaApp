package com.example.projectforjava.fragments.leaderboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import com.example.projectforjava.templates.leaderboard.ChallengeOnLeaderboard;
import com.example.projectforjava.templates.leaderboard.UserOnLeaderboard;
import com.example.projectforjava.utils.ImgUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final Random random = new Random();
    public static List<ChallengeOnLeaderboard> generateChallenges(Context context, int count) {
        List<ChallengeOnLeaderboard> challenges = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // Создаем случайное изображение
            Drawable image = generateRandomDrawableImage(context);

            String title = "Challenge " + (i + 1);
            String description = "Description for challenge " + (i + 1);
            int completions = random.nextInt(1000); // случайное число выполнений

            challenges.add(new ChallengeOnLeaderboard(image, title, description, completions));
        }
        return challenges;
    }

    private static Drawable generateRandomDrawableImage(Context context) {
        int width = 150;
        int height = 150;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // Задаем случайный цвет фона
        paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
        canvas.drawRect(0, 0, width, height, paint);

        // Рисуем случайные круги
        Random random = new Random();
        int circleCount = randomInt(1, 10);
        for (int i = 0; i < circleCount; i++) {
            paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
            canvas.drawCircle(randomInt(0, width), randomInt(0, height), randomInt(10, 50), paint);
        }

        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static List<UserOnLeaderboard> generateUsers(Context context, int count) {
        List<UserOnLeaderboard> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Drawable image = generateRandomDrawableIcon(context);

            String name = "User " + (i + 1);
            int personalLvl = random.nextInt(100);
            int totalGlobalChallengesCompleted = random.nextInt(1000);

            users.add(new UserOnLeaderboard(image, name, personalLvl, totalGlobalChallengesCompleted));
        }
        return users;
    }

    private static Drawable generateRandomDrawableIcon(Context context) {
        int size = 150;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // Рисуем круг с фоном случайного цвета
        paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        // Рисуем дополнительные случайные круги
        int circleCount = randomInt(1, 10);
        for (int i = 0; i < circleCount; i++) {
            paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
            int radius = randomInt(10, size / 4);
            canvas.drawCircle(randomInt(0, size), randomInt(0, size), radius, paint);
        }

        return new  BitmapDrawable(context.getResources(), ImgUtils.getRoundedBitmap(bitmap));
    }

    private static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
