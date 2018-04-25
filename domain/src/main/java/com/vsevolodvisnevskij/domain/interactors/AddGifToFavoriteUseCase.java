package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddGifToFavoriteUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public AddGifToFavoriteUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Completable addToFavorites(String id, String path) {
        return gifRepository.addToFavorites(id, path).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }

}
