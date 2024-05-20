package com.example.projectforjava.templates.leaderboard;

import android.graphics.drawable.Drawable;

public class UserOnLeaderboard {
    Drawable drawable;
    String name;
    int personalLvl;
    int totalGlobalChallengesCompleted;

    public UserOnLeaderboard(Drawable drawable, String name, int personalLvl, int totalGlobalChallengesCompleted) {
        this.drawable = drawable;
        this.name = name;
        this.personalLvl = personalLvl;
        this.totalGlobalChallengesCompleted = totalGlobalChallengesCompleted;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonalLvl() {
        return personalLvl;
    }

    public void setPersonalLvl(int personalLvl) {
        this.personalLvl = personalLvl;
    }

    public int getTotalGlobalChallengesCompleted() {
        return totalGlobalChallengesCompleted;
    }

    public void setTotalGlobalChallengesCompleted(int totalGlobalChallengesCompleted) {
        this.totalGlobalChallengesCompleted = totalGlobalChallengesCompleted;
    }
}
