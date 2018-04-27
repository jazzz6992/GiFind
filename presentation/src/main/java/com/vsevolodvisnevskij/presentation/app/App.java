package com.vsevolodvisnevskij.presentation.app;

import android.app.Application;

import com.vsevolodvisnevskij.presentation.injection.AppComponent;
import com.vsevolodvisnevskij.presentation.injection.AppModule;
import com.vsevolodvisnevskij.presentation.injection.DaggerAppComponent;


/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

public class App extends Application {
    public static final String TAG = "my_tag";

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
