package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DownloadGifFileUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public DownloadGifFileUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Observable<File> download(String link, String name) {
        return gifRepository.download(link, name).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
