package com.vsevolodvisnevskij.presentation.executor;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by vsevolodvisnevskij on 16.03.2018.
 */

public class UIThread implements PostExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
