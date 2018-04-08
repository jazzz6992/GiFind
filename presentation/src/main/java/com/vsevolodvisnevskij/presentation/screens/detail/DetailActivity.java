package com.vsevolodvisnevskij.presentation.screens.detail;

import android.content.Context;
import android.content.Intent;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.ActivityDetailBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMActivity;

public class DetailActivity extends BaseMVVMActivity<ActivityDetailBinding, SingleGifViewModel, DetailRouter> {


    private static final String EXTRA_URL = "URL";

    @Override
    public int provideLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public SingleGifViewModel provideViewModel() {
        SingleGifViewModel singleGifViewModel = new SingleGifViewModel();
        singleGifViewModel.setGifUrl(getIntent().getStringExtra(EXTRA_URL));
        return singleGifViewModel;
    }

    @Override
    public DetailRouter provideRouter() {
        return new DetailRouter(this);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }
}
