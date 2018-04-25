package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.Bindable;

import com.vsevolodvisnevskij.presentation.base.BaseItemViewModel;

public class ItemPathGifViewModel extends BaseItemViewModel<String> {
    @Bindable
    public String getName() {
        return getItem();
    }
}
