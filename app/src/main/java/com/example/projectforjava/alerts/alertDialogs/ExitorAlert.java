package com.example.projectforjava.alerts.alertDialogs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.projectforjava.R;

// ExitorAlert.java
public class ExitorAlert {
    public static void showExitDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exit, null);
        builder.setView(dialogView);

        Button buttonYes = dialogView.findViewById(R.id.exit_button_yes);
        Button buttonNo = dialogView.findViewById(R.id.exit_button_no);

        final AlertDialog dialog = builder.create();

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finishAffinity(); // Завершить все активности в стеке
                //System.exit(0); // Завершить процесс приложения, СКОРЕЕ ВСЕГО ЭТО НАДО УБРАТЬ
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Просто закрываем диалоговое окно
            }
        });

        dialog.show();
    }
}
