package com.example.projectforjava.alerts.interfaces;

public interface ChallengeDialogListener {
    void onReject(int position);
    void onComplete(int position);
    void onCancel();
}