package com.example.projectforjava.templates.friends;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.projectforjava.utils.ImgUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FriendsChallenge implements Parcelable {
    private Drawable image;
    private String title;
    private String description;
    private ArrayList<String> receiversNames;
    private String senderName;

    public FriendsChallenge(Drawable image, String title, String description, ArrayList<String> receiversNames, String senderName) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.receiversNames = receiversNames;
        this.senderName = senderName;
    }

    protected FriendsChallenge(Parcel in) {
        byte[] byteArray = in.createByteArray();
        assert byteArray != null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        this.image = new BitmapDrawable(Resources.getSystem(), bitmap);
        this.title = in.readString();
        this.description = in.readString();
        this.receiversNames = in.createStringArrayList();
        this.senderName = in.readString();
    }

    public static final Creator<FriendsChallenge> CREATOR = new Creator<FriendsChallenge>() {
        @Override
        public FriendsChallenge createFromParcel(Parcel in) {
            return new FriendsChallenge(in);
        }

        @Override
        public FriendsChallenge[] newArray(int size) {
            return new FriendsChallenge[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bitmap bitmap = ImgUtils.drawableToBitmap(this.image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        dest.writeByteArray(byteArray);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeStringList(this.receiversNames);
        dest.writeString(this.senderName);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public ArrayList<String> getReceiversNames() {
        return receiversNames;
    }

    public void setReceiversNames(ArrayList<String> receiversNames) {
        this.receiversNames = receiversNames;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}