package com.robocat.android.rc.persistence.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface RemoteDeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insert(RemoteDevice device);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public Completable update(RemoteDevice device);

    @Delete
    public Completable delete(RemoteDevice device);

    @Query("SELECT * FROM REMOTEDEVICE WHERE uuid = :uuid")
    public Single<RemoteDevice> getRemoteDevice(UUID uuid);

    @Query("SELECT * FROM REMOTEDEVICE")
    public Single<List<RemoteDevice>> getAllRemoteDevices();
}
