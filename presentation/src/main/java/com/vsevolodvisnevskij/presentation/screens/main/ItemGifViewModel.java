package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.Bindable;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.presentation.base.BaseItemViewModel;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class ItemGifViewModel extends BaseItemViewModel<Gif> {

    @Bindable
    public String getName() {
        return getItem().getPreviewUrl();
    }

}
