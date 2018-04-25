package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class RemoveGifFromFavoritesUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public RemoveGifFromFavoritesUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Completable remove(String id) {
        return gifRepository.removeFromFavorites(id).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
