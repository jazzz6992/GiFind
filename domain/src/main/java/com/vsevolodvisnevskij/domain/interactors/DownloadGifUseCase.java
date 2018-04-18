package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DownloadGifUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public DownloadGifUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Observable<File> download(String link) {
        return gifRepository.download(link).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
