package com.vsevolodvisnevskij.presentation.base;

import android.app.Activity;

public class Router {
    private Activity activity;

    public Router(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void back() {
        getActivity().onBackPressed();
    }
}
