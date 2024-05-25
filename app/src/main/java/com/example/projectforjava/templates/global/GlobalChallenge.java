package com.example.projectforjava.templates.global;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class GlobalChallenge implements Parcelable {
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
    boolean isAccepted;

    public GlobalChallenge(Drawable image, String title, String description, int approximateTimeValue, String approximateTimeMeasure, int completions, int author_id, int likes, int warnings, boolean isLiked, boolean isWarned, boolean isAccepted) {
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
        this.isAccepted = isAccepted;
    }

    protected GlobalChallenge(Parcel in) {
        byte[] byteArray = in.createByteArray();
        if (byteArray != null) {
            Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
            this.image = new BitmapDrawable(bitmap);
        }
        title = in.readString();
        description = in.readString();
        approximateTimeValue = in.readInt();
        approximateTimeMeasure = in.readString();
        completions = in.readInt();
        author_id = in.readInt();
        likes = in.readInt();
        warnings = in.readInt();
        isLiked = in.readByte() != 0;
        isWarned = in.readByte() != 0;
        isAccepted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Handle image conversion
        if (this.image != null) {
            Bitmap bitmap = ((BitmapDrawable) this.image).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            dest.writeByteArray(byteArray);
        } else {
            dest.writeByteArray(null);
        }
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(approximateTimeValue);
        dest.writeString(approximateTimeMeasure);
        dest.writeInt(completions);
        dest.writeInt(author_id);
        dest.writeInt(likes);
        dest.writeInt(warnings);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeByte((byte) (isWarned ? 1 : 0));
        dest.writeByte((byte) (isAccepted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<GlobalChallenge> CREATOR = new Creator<GlobalChallenge>() {
        @Override
        public GlobalChallenge createFromParcel(Parcel in) {
            return new GlobalChallenge(in);
        }

        @Override
        public GlobalChallenge[] newArray(int size) {
            return new GlobalChallenge[size];
        }
    };

    // Getters and setters...

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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}