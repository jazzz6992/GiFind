package com.vsevolodvisnevskij.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.vsevolodvisnevskij.data.entity.db.GifDB;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface GifDao {

    @Insert
    void insert(GifDB gifDB);

    @Query("SELECT * FROM Gif")
    Flowable<List<GifDB>> getAll();

    @Query("SELECT * FROM Gif WHERE id=:id")
    Flowable<List<GifDB>> getById(String id);


    @Query("DELETE FROM Gif WHERE id=:id")
    void delete(String id);

}
