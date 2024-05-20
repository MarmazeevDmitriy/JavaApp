package com.example.projectforjava.templates.friends;

import android.graphics.drawable.Drawable;

public class FriendRequest {
    Drawable icon;
    String name;
    int friendsTotal;
    int globalChallengesCompleted;

    public FriendRequest(Drawable icon, String name, int friendsTotal, int globalChallengesCompleted) {
        this.icon = icon;
        this.name = name;
        this.friendsTotal = friendsTotal;
        this.globalChallengesCompleted = globalChallengesCompleted;
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

    public void setGlobalChallengesCompleted(int globalChallengesCompleted) {
        this.globalChallengesCompleted = globalChallengesCompleted;
    }
}
