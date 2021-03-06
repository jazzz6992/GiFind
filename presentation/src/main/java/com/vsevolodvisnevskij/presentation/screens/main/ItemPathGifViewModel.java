package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.Bindable;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.presentation.base.BaseItemViewModel;

public class ItemPathGifViewModel extends ItemGifViewModel {
    @Bindable
    public String getName() {
        return getItem().getPath();
    }
}
