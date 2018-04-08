package com.vsevolodvisnevskij.presentation.screens.main;

import android.app.Activity;
import android.content.Intent;

import com.vsevolodvisnevskij.presentation.base.Router;
import com.vsevolodvisnevskij.presentation.screens.detail.DetailActivity;

public class MainRouter extends Router {
    public MainRouter(Activity activity) {
        super(activity);
    }

    public void navigateToDetailActivity(String url) {
        Intent intent = DetailActivity.newIntent(getActivity(), url);
        getActivity().startActivity(intent);
    }
}
