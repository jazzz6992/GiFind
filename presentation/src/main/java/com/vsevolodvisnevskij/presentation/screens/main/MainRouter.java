package com.vsevolodvisnevskij.presentation.screens.main;

import android.app.Activity;
import android.content.Intent;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.presentation.base.BaseRouter;
import com.vsevolodvisnevskij.presentation.screens.detail.DetailActivity;

public class MainRouter extends BaseRouter {
    public MainRouter(Activity activity) {
        super(activity);
    }

    public void navigateToDetailActivity(Gif g) {
        Intent intent = DetailActivity.newIntent(getActivity(), g);
        getActivity().startActivity(intent);
    }
}
