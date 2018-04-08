package com.vsevolodvisnevskij.presentation.base;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vsevolodvisnevskij on 12.03.2018.
 */

public abstract class BaseViewModel<R extends Router> extends ViewModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Nullable
    protected R router;

    public void attachRouter(R router) {
        this.router = router;
    }

    public void detachRouter() {
        router = null;
    }

    public abstract void createInject();

    public BaseViewModel() {
        createInject();
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStart() {

    }

    public void onStop() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
