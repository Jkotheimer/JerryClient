package com.robocat.android.rc.persistence.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class RemoteDevice implements Parcelable {

    /**
     * ---------------------------
     * REMOTEDEVICE TABLE COLUMNS
     * ---------------------------
     */

    @PrimaryKey
    public UUID uuid;
    public String name;
    public Boolean isDefault;

    /**
     * ------------------------
     * PARCELABLE REQUIREMENTS
     * ------------------------
     */

    @Ignore
    protected RemoteDevice(Parcel in) {
        uuid = UUID.fromString(in.readString());
        name = in.readString();
        isDefault = in.readByte() != 0;
    }

    @Ignore
    public static final Creator<RemoteDevice> CREATOR = new Creator<RemoteDevice>() {
        @Override
        public RemoteDevice createFromParcel(Parcel in) {
            return new RemoteDevice(in);
        }

        @Override
        public RemoteDevice[] newArray(int size) {
            return new RemoteDevice[size];
        }
    };

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid.toString());
        dest.writeString(name);
        dest.writeByte((byte) (isDefault ? 1 : 0));
    }
}
