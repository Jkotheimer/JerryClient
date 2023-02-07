package com.robocat.android.rc.persistence.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class RemoteDevice {
    @PrimaryKey
    public UUID uuid;

    public String name;
    public Boolean isDefault;
}
