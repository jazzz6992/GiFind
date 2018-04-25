package com.vsevolodvisnevskij.presentation.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vsevolodvisnevskij.data.db.AppDatabase;
import com.vsevolodvisnevskij.data.net.RestApi;
import com.vsevolodvisnevskij.data.net.RestService;
import com.vsevolodvisnevskij.data.repository.GifRepositoryImpl;
import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;
import com.vsevolodvisnevskij.presentation.executor.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

@Module
public class AppModule {


    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }

    @Provides
    @Singleton
    public PostExecutionThread getUiThread() {
        return new UIThread();
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl("https://api.giphy.com/").build();
    }

    @Provides
    @Singleton
    public RestApi getRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    public GifRepository getGitRepository(Context context, RestService restService, AppDatabase appDatabase) {
        return new GifRepositoryImpl(context, restService, appDatabase);
    }

    @Provides
    @Singleton
    public AppDatabase getAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database").fallbackToDestructiveMigration().build();

    }

}
