package com.vsevolodvisnevskij.presentation.screens.detail;

import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

public class SingleGifViewModel extends BaseViewModel<DetailRouter> {
    private ObservableField<String> gifUrl = new ObservableField<>();

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }

    public String getGifUrl() {
        return gifUrl.get();
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl.set(gifUrl);
    }
}
