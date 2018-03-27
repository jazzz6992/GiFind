package com.vsevolodvisnevskij.data.repository;

import android.content.Context;

import com.vsevolodvisnevskij.data.constants.Constants;
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
    public Observable<List<Gif>> getTaradingGifs(String offset) {
        return restService.loadTrending(Constants.KEY, offset);
    }

    @Override
    public Observable<List<Gif>> searchGifs(String offset, String search) {
        return restService.loadSearch(Constants.KEY, offset, search);
    }
}
