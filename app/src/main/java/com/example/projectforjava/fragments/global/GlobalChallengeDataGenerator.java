package com.example.projectforjava.fragments.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.projectforjava.templates.global.GlobalChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlobalChallengeDataGenerator {

    public static List<GlobalChallenge> generateGlobalChallenges(Context context, int count) {
        List<GlobalChallenge> challenges = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Drawable image = generateRandomDrawable(context);
            String title = "Challenge " + (i + 1);
            String description = "Description for challenge " + (i + 1);
            int approximateTimeValue = random.nextInt(60) + 1;
            String approximateTimeMeasure = random.nextBoolean() ? "minutes" : "hours";
            int completions = random.nextInt(1000);
            int author_id = random.nextInt(100);
            int likes = random.nextInt(500);
            int warnings = random.nextInt(50);
            boolean isLiked = random.nextBoolean();
            boolean isWarned = random.nextBoolean();
            boolean isAccepted = random.nextBoolean();

            challenges.add(new GlobalChallenge(
                    image,
                    title,
                    description,
                    approximateTimeValue,
                    approximateTimeMeasure,
                    completions,
                    author_id,
                    likes,
                    warnings,
                    isLiked,
                    isWarned,
                    isAccepted
            ));
        }
        return challenges;
    }

    private static Drawable generateRandomDrawable(Context context) {
        int width = 150;
        int height = 150;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // Задаем случайный цвет фона
        paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
        canvas.drawRect(0, 0, width, height, paint);

        // Рисуем случайное количество кругов
        int circleCount = randomInt(1, 10);
        for (int i = 0; i < circleCount; i++) {
            paint.setColor(Color.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)));
            canvas.drawCircle(randomInt(0, width), randomInt(0, height), randomInt(10, 50), paint);
        }

        // Делает изображение круглым
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas roundCanvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint roundPaint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = width / 2;

        roundPaint.setAntiAlias(true);
        roundCanvas.drawARGB(0, 0, 0, 0);
        roundPaint.setColor(color);
        roundCanvas.drawOval(rectF, roundPaint);

        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        roundCanvas.drawBitmap(bitmap, rect, rect, roundPaint);

        return new BitmapDrawable(context.getResources(), output);
    }

    private static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
