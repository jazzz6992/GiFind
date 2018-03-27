package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 27.03.2018.
 */

public class SearchGifsUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public SearchGifsUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Observable<List<Gif>> get(String q, String offset) {
        return gifRepository.searchGifs(q, offset).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
