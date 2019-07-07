package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.executor.ThreadExecutor;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vsevolodvisnevskij on 16.03.2018.
 */

public abstract class BaseUseCase {
    protected Scheduler postExecutionThread;
    protected Scheduler threadExecution;

    public BaseUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread.getScheduler();
        threadExecution = Schedulers.io();
    }
}
