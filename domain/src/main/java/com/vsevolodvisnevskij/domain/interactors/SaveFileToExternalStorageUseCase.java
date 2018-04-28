package com.vsevolodvisnevskij.domain.interactors;

import com.vsevolodvisnevskij.domain.executor.PostExecutionThread;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SaveFileToExternalStorageUseCase extends BaseUseCase {
    private GifRepository gifRepository;

    @Inject
    public SaveFileToExternalStorageUseCase(PostExecutionThread postExecutionThread, GifRepository gifRepository) {
        super(postExecutionThread);
        this.gifRepository = gifRepository;
    }

    public Completable saveFileToExternalStorage(String name, String path) {
        return gifRepository.saveFileToExternalStorage(name, path).subscribeOn(threadExecution).observeOn(postExecutionThread);
    }
}
