package com.example.projectforjava.customElements;

import static com.example.projectforjava.utils.ImgUtils.drawableToBitmap;
import static com.example.projectforjava.utils.ImgUtils.scaleRectangleBitmap;
import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.projectforjava.R;

public class CustomSearchEditText extends AppCompatEditText {

    public CustomSearchEditText(Context context) {
        super(context);
        init();
    }

    public CustomSearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Получаем изображение в исходном размере
        Drawable originalDrawable = ContextCompat.getDrawable(getContext(), R.drawable.search_loupe);

        if (originalDrawable != null) {
            // Применяем масштабирование к изображению
            Bitmap scaledBitmap = scaleRectangleBitmap(drawableToBitmap(originalDrawable), dpToPx(6), dpToPx(6));

            // Создаем Drawable из масштабированного Bitmap
            Drawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);

            // Устанавливаем изображение внутрь поля ввода
            setCompoundDrawablesWithIntrinsicBounds(null, null, scaledDrawable, null);
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
