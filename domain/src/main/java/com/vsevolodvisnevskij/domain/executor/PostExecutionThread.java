package com.vsevolodvisnevskij.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created by vsevolodvisnevskij on 16.03.2018.
 */

public interface PostExecutionThread {
    Scheduler getScheduler();

}
