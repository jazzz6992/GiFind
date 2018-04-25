package com.vsevolodvisnevskij.presentation.screens.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.ActivityMainBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMActivity;
import com.vsevolodvisnevskij.presentation.base.BaseRouter;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding, MainViewModel, BaseRouter> {

    @Override
    public int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel provideViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setupAdapter(getSupportFragmentManager(), getResources());
        return mainViewModel;
    }

    @Override
    public BaseRouter provideRouter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.tabs.setupWithViewPager(binding.vpager);
    }
}
