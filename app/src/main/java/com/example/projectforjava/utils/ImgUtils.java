package com.example.projectforjava.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImgUtils {
    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int diameter = Math.min(width, height);

        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, (width - diameter) / 2, (height - diameter) / 2, diameter, diameter);
        Bitmap output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final android.graphics.Rect rect = new android.graphics.Rect(0, 0, diameter, diameter);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(croppedBitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getRoundedSquareBitmap(Bitmap bitmap, int targetSize, int cornerRadius) {
        // Создаем квадратное изображение
        Bitmap output = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        RectF rect = new RectF(0, 0, targetSize, targetSize);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        return output;
    }

    public static Bitmap scaleSquareBitmap(Bitmap bitmap, int targetSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Определяем минимальную сторону для масштабирования
        int newSize = Math.min(width, height);

        // Масштабируем изображение до минимальной стороны
        float scale = (float) targetSize / newSize;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Вычисляем смещение для центрирования изображения
        int deltaX = Math.abs(width - newSize) / 2;
        int deltaY = Math.abs(height - newSize) / 2;

        // Обрезаем изображение до квадрата с центрированием

        return Bitmap.createBitmap(bitmap, deltaX, deltaY, newSize, newSize, matrix, true);
    }

    public static Bitmap scaleRectangleBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        // Рассчитываем коэффициенты масштабирования для уменьшения размера изображения
        float widthScale = (float) maxWidth / originalWidth;
        float heightScale = (float) maxHeight / originalHeight;

        // Выбираем меньший коэффициент масштабирования, чтобы изображение не превышало максимальные размеры
        float scale = Math.min(widthScale, heightScale);

        // Создаем матрицу масштабирования
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Масштабируем изображение с помощью матрицы масштабирования
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);
    }
}
