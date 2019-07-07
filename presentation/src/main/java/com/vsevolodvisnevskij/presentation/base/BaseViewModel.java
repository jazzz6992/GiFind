package com.vsevolodvisnevskij.presentation.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vsevolodvisnevskij on 12.03.2018.
 */

public abstract class BaseViewModel<R extends BaseRouter> extends ViewModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ObservableField<String> errorMessage = new ObservableField<>();
    @Nullable
    protected R router;

    public void attachRouter(R router) {
        this.router = router;
    }

    public void detachRouter() {
        router.releaseActivity();
        router = null;
    }

    public abstract void createInject();

    public BaseViewModel() {
        createInject();
    }

    public void handleError(Throwable e) {

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

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return (netInfo != null && netInfo.isConnected());
    }
}
