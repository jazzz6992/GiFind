package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetLocalGifByIdUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public GetLocalGifByIdUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Flowable<Gif> getLocalGifById(String id) {
        return gifRepository.getLocalGifById(id).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
