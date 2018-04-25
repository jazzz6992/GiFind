package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteGifFileUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public DeleteGifFileUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Completable delete(String name) {
        return gifRepository.delete(name).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
