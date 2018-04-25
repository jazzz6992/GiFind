package com.vsevolodvisnevskij.presentation.base;

import android.app.Activity;

public class BaseRouter {
    private Activity activity;

    public BaseRouter(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void back() {
        getActivity().onBackPressed();
    }
}
