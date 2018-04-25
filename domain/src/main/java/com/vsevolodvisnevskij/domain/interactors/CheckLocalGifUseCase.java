package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class CheckLocalGifUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public CheckLocalGifUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Flowable<Boolean> check(String id) {
        return gifRepository.checkLocalGif(id).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
