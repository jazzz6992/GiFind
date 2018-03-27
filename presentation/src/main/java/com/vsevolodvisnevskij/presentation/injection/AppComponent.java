package com.vsevolodvisnevskij.presentation.injection;

import com.vsevolodvisnevskij.presentation.screens.PhotosViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(PhotosViewModel photosViewModel);
}
