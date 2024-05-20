package com.example.projectforjava.alerts.alertDialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.projectforjava.R;
import com.example.projectforjava.alerts.interfaces.FriendChallengeDialogListener;

public class FriendChallengeAlert {
    public static void showFriendChallengeDialog(Context context, int position, FriendChallengeDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_friend_challenge, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        Button btnReject = dialogView.findViewById(R.id.btnReject);
        Button btnComplete = dialogView.findViewById(R.id.btnComplete);

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                listener.onCancel();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReject(position);
                alertDialog.dismiss();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onComplete(position);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
