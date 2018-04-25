package com.vsevolodvisnevskij.presentation.screens.main;

import android.content.res.Resources;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseRouter;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

public class MainViewModel extends BaseViewModel<BaseRouter> {

    private ObservableField<PagerAdapter> pagerAdapter = new ObservableField<>();

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }

    public void setupAdapter(FragmentManager manager, Resources resources) {
        final int PAGE_COUNT = 2;
        final String tabTitles[] = resources.getStringArray(R.array.tab_names);

        pagerAdapter.set(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new WebGifsFragment();
                    case 1:
                        return new LocalGifsFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });
    }

    public PagerAdapter getAdapter() {
        return pagerAdapter.get();
    }
}
