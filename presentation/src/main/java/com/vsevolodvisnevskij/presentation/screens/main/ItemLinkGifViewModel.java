package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.Bindable;

public class ItemLinkGifViewModel extends ItemGifViewModel {
    @Bindable
    public String getName() {
        return getItem().getPreviewUrl();
    }
}
