package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 21.03.2018.
 */

public class GetTrandingGifsUseCase extends BaseUseCase {
    private GifRepository userRepository;

    @Inject
    public GetTrandingGifsUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.userRepository = gifRepository;
    }

    public Observable<List<Gif>> get(String key, String offset) {
        return userRepository.getGifs(key, offset).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
