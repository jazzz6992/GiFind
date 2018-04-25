package com.vsevolodvisnevskij.presentation.screens.detail;

import android.content.Context;
import android.databinding.ObservableField;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.interactors.AddGifToFavoriteUseCase;
import com.vsevolodvisnevskij.domain.interactors.CheckLocalGifUseCase;
import com.vsevolodvisnevskij.domain.interactors.DeleteGifFileUseCase;
import com.vsevolodvisnevskij.domain.interactors.DownloadGifFileUseCase;
import com.vsevolodvisnevskij.domain.interactors.RemoveGifFromFavoritesUseCase;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;

public class SingleGifViewModel extends BaseViewModel<DetailRouter> {
    @Inject
    public Context context;
    @Inject
    public DownloadGifFileUseCase downloadGifFileUseCase;
    @Inject
    public DeleteGifFileUseCase deleteGifFileUseCase;
    @Inject
    public AddGifToFavoriteUseCase addGifToFavoriteUseCase;
    @Inject
    public RemoveGifFromFavoritesUseCase removeGifFromFavoritesUseCase;
    @Inject
    public CheckLocalGifUseCase checkLocalGifUseCase;
    private PublishSubject<Completable> publishSubject = PublishSubject.create();
    private boolean local;
    private Gif gif;
    private File file;
    private ObservableField<String> gifUrl = new ObservableField<>();
    private final String tmpName = "tmp.gif";

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }

    public void setGif(Gif gif) {
        this.gif = gif;
        gifUrl.set(gif.getOriginalUrl());
    }

    public PublishSubject<Completable> getPublishSubject() {
        return publishSubject;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getGifUrl() {
        return gifUrl.get();
    }

    public void share() {
        if (local) {
            if (router != null) {
                router.navigateToActivityChooser(file);
            }
        } else {
            compositeDisposable.add(downloadGifFileUseCase.download(gifUrl.get(), tmpName).subscribe(f -> {
                if (router != null) {
                    router.navigateToActivityChooser(f);
                }
            }));
        }
    }

    public String getContent() {
        if (local) {
            return file.getAbsolutePath();
        } else {
            return getGifUrl();
        }
    }

    public void addToFavorite() {
        compositeDisposable.add(downloadGifFileUseCase.download(gifUrl.get(), getFileName()).doOnNext(file -> {
            compositeDisposable.add(addGifToFavoriteUseCase.addToFavorites(gif.getId(), file.getAbsolutePath()).subscribe());
        }).subscribe(f -> {
            publishSubject.onNext(Completable.complete());
        }));
    }

    public void removeFromFavorites() {
        compositeDisposable.add(deleteGifFileUseCase.delete(getFileName()).doOnComplete(() -> {
            compositeDisposable.add(removeGifFromFavoritesUseCase.remove(getId()).subscribe());
        }).subscribe(() -> {
            if (!local) {
                publishSubject.onNext(Completable.complete());
            } else {
                router.back();
            }
        }));
    }

    public Flowable<Boolean> checkFavorite() {
        if (!local) {
            return checkLocalGifUseCase.check(gif.getId());
        } else
            return Flowable.just(true);
    }

    private String getFileName() {
        if (!local) {
            return gif.getId() + ".gif";
        } else {
            return file.getName();
        }
    }

    private String getId() {
        if (!local) {
            return gif.getId();
        } else {
            return file.getName().substring(0, file.getName().lastIndexOf("."));
        }
    }

    public void toFromFavorites() {
        if (!local) {
            compositeDisposable.add(checkFavorite().take(1).subscribe(b -> {
                if (b) {
                    removeFromFavorites();
                } else {
                    addToFavorite();
                }
            }));
        } else {
            removeFromFavorites();
        }
    }
}
