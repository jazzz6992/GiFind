package com.vsevolodvisnevskij.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created by vsevolodvisnevskij on 16.03.2018.
 */
// этот интерфейс нужно реализовать в presentation
public interface PostExecutionThread {
    Scheduler getScheduler();

}
