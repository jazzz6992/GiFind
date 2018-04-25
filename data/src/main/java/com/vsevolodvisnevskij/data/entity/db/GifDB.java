package com.vsevolodvisnevskij.data.entity.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "Gif")
public class GifDB {
    @NonNull
    @PrimaryKey
    private String id;
    private String path;

    public GifDB(@NonNull String id, String path) {
        this.id = id;
        this.path = path;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
