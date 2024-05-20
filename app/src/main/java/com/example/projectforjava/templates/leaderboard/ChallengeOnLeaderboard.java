package com.example.projectforjava.templates.leaderboard;

import android.graphics.drawable.Drawable;

public class ChallengeOnLeaderboard {
    Drawable image;
    String title;
    String description;
    int completions;

    public ChallengeOnLeaderboard(Drawable image, String title, String description, int completions) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.completions = completions;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompletions() {
        return completions;
    }

    public void setCompletions(int completions) {
        this.completions = completions;
    }
}
