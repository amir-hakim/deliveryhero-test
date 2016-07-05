package com.test.deliveryhero.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Photo model, to hold Photo attributes
 */
public class Photo implements Parcelable{
    int albumId;
    int id;
    public String title;
    public String url;
    public String thumbnailUrl;

    /**
     * Photo Constructor
     * @param albumId
     * @param id
     * @param title
     * @param url
     * @param thumbnailUrl
     */
    public Photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }
    // "De-parcel object
    public Photo(Parcel in) {
        albumId = in.readInt();
        id = in.readInt();
        title = in.readString();
        url = in.readString();
        thumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(albumId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
    }
    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
