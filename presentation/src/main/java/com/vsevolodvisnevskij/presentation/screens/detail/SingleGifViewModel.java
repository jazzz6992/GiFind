package com.vsevolodvisnevskij.presentation.screens.detail;

import android.content.Context;
import android.databinding.ObservableField;

import com.vsevolodvisnevskij.domain.interactors.DownloadGifUseCase;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

import javax.inject.Inject;

public class SingleGifViewModel extends BaseViewModel<DetailRouter> {
    private ObservableField<String> gifUrl = new ObservableField<>();
    @Inject
    public Context context;
    @Inject
    public DownloadGifUseCase downloadGifUseCase;

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }

    public String getGifUrl() {
        return gifUrl.get();
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl.set(gifUrl);
    }

    public void share() {
        compositeDisposable.add(downloadGifUseCase.download(gifUrl.get()).subscribe(f -> {
            if (router != null) {
                router.navigateToActivityChooser(f);
            }
        }));
    }
}
