package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetLocalGifsUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public GetLocalGifsUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Flowable<List<Gif>> getLocalGifs() {
        return gifRepository.getLocalGifs().subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
