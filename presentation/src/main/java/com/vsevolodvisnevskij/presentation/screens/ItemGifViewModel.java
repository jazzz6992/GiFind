package com.vsevolodvisnevskij.presentation.screens;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.vsevolodvisnevskij.domain.entity.Gif;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class ItemGifViewModel extends BaseObservable {
    private Gif entity;

    public Gif getEntity() {
        return entity;
    }

    public void setEntity(Gif entity) {
        this.entity = entity;
        notifyChange();
    }

    @Bindable
    public String getName() {
        return entity.getUrl();
    }

}
