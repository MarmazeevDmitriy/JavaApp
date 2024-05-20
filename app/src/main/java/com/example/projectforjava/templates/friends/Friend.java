package com.example.projectforjava.templates.friends;

import android.graphics.drawable.Drawable;

public class Friend {
    Drawable icon;
    String name;
    int challengesSent;
    int challengesCompleted;

    public Friend(Drawable icon, String name, int challengesSent, int challengesCompleted) {
        this.icon = icon;
        this.name = name;
        this.challengesSent = challengesSent;
        this.challengesCompleted = challengesCompleted;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChallengesSent() {
        return challengesSent;
    }

    public void setChallengesSent(int challengesSent) {
        this.challengesSent = challengesSent;
    }

    public int getChallengesCompleted() {
        return challengesCompleted;
    }

    public void setChallengesCompleted(int challengesCompleted) {
        this.challengesCompleted = challengesCompleted;
    }
}
