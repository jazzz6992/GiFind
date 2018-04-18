package com.vsevolodvisnevskij.domain.repository;

import com.vsevolodvisnevskij.domain.entity.Gif;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public interface GifRepository {
    Observable<List<Gif>> searchGifs(String offset, String search);
    Observable<List<Gif>> getTaradingGifs(String offset);


    Observable<File> download(String link);
}
