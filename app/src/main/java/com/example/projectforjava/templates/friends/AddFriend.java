package com.example.projectforjava.templates.friends;

import android.graphics.drawable.Drawable;

public class AddFriend {
    Drawable icon;
    String name;
    int friendsTotal;
    int globalChallengesCompleted;
    boolean requestSent = false;

    public AddFriend(Drawable icon, String name, int friendsTotal, int globalChallengesCompleted, boolean requestSent) {
        this.icon = icon;
        this.name = name;
        this.friendsTotal = friendsTotal;
        this.globalChallengesCompleted = globalChallengesCompleted;
        this.requestSent = requestSent;
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

    public int getFriendsTotal() {
        return friendsTotal;
    }

    public void setFriendsTotal(int friendsTotal) {
        this.friendsTotal = friendsTotal;
    }

    public int getGlobalChallengesCompleted() {
        return globalChallengesCompleted;
    }

    public void setGlobalChallengesCompleted(int globalChallengesTotal) {
        this.globalChallengesCompleted = globalChallengesTotal;
    }

    public boolean isRequestSent() {
        return requestSent;
    }

    public void setRequestSent(boolean requestSent) {
        this.requestSent = requestSent;
    }
}
