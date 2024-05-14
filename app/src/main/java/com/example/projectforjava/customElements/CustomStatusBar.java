package com.example.projectforjava.customElements;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

public class CustomStatusBar {
    public static void changeStatusBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.BLACK);
        }
    }
}
