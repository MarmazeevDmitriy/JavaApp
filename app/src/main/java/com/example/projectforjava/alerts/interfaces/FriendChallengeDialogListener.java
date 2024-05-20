package com.example.projectforjava.alerts.interfaces;

public interface FriendChallengeDialogListener {
    void onReject(int position);
    void onComplete(int position);
    void onCancel();
}