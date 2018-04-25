package com.vsevolodvisnevskij.presentation.injection;

import com.vsevolodvisnevskij.presentation.screens.detail.SingleGifViewModel;
import com.vsevolodvisnevskij.presentation.screens.main.LocalGifsViewModel;
import com.vsevolodvisnevskij.presentation.screens.main.MainViewModel;
import com.vsevolodvisnevskij.presentation.screens.main.WebGifsViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(WebGifsViewModel webGifsViewModel);

    void inject(LocalGifsViewModel localGifsViewModel);

    void inject(SingleGifViewModel singleGifViewModel);

    void inject(MainViewModel mainViewModel);
}
