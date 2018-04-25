package com.vsevolodvisnevskij.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vsevolodvisnevskij.data.entity.db.GifDB;

@Database(entities = {GifDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GifDao getGifDao();
}
