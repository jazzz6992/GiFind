package com.vsevolodvisnevskij.data.repository;

import android.content.Context;

import com.vsevolodvisnevskij.data.net.RestService;
import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class GifRepositoryImpl implements GifRepository {
    private RestService restService;
    private Context context;

    @Inject
    public GifRepositoryImpl(Context context, RestService restService) {
        this.context = context;
        this.restService = restService;
    }

    @Override
    public Observable<List<Gif>> getGifs(String... query) {
        switch (query.length) {
            case 2:
                return restService.loadTrending(query[0], query[1]);
            case 3:
                return restService.loadSearch(query[0], query[1], query[2]);
        }
        return null;
    }
}
