package com.example.projectforjava.templates.global;

import android.graphics.drawable.Drawable;

public class GlobalChallenge {
    Drawable image;
    String title;
    String description;
    int approximateTimeValue;
    String approximateTimeMeasure;
    int completions;
    int author_id;
    int likes;
    int warnings;
    boolean isLiked;
    boolean isWarned;

    public GlobalChallenge(Drawable image, String title, String description, int approximateTimeValue, String approximateTimeMeasure, int completions, int author_id, int likes, int warnings, boolean isLiked, boolean isWarned) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.approximateTimeValue = approximateTimeValue;
        this.approximateTimeMeasure = approximateTimeMeasure;
        this.completions = completions;
        this.author_id = author_id;
        this.likes = likes;
        this.warnings = warnings;
        this.isLiked = isLiked;
        this.isWarned = isWarned;
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

    public int getApproximateTimeValue() {
        return approximateTimeValue;
    }

    public void setApproximateTimeValue(int approximateTimeValue) {
        this.approximateTimeValue = approximateTimeValue;
    }

    public String getApproximateTimeMeasure() {
        return approximateTimeMeasure;
    }

    public void setApproximateTimeMeasure(String approximateTimeMeasure) {
        this.approximateTimeMeasure = approximateTimeMeasure;
    }

    public int getCompletions() {
        return completions;
    }

    public void setCompletions(int completions) {
        this.completions = completions;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isWarned() {
        return isWarned;
    }

    public void setWarned(boolean warned) {
        isWarned = warned;
    }
}
