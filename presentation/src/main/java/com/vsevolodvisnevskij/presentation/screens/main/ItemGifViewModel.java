package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.Bindable;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.presentation.base.BaseItemViewModel;

public abstract class ItemGifViewModel extends BaseItemViewModel<Gif> {

    @Bindable
    public abstract String getName();
}
