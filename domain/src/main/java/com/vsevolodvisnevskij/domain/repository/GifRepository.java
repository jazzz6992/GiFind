package com.vsevolodvisnevskij.domain.repository;

import com.vsevolodvisnevskij.domain.entity.Gif;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public interface GifRepository {
    Observable<List<Gif>> getGifs(String... q);
}
