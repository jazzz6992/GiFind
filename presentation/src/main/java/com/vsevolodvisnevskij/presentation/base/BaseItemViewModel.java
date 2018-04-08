package com.vsevolodvisnevskij.presentation.base;

import android.databinding.BaseObservable;

public abstract class BaseItemViewModel<Model> extends BaseObservable {
    private Model item;

    public void init() {
    }

    public void release() {

    }

    public Model getItem() {
        return item;
    }

    public void setItem(Model item) {
        this.item = item;
        notifyChange();
    }
}
