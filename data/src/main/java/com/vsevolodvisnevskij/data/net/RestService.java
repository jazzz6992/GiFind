package com.vsevolodvisnevskij.data.net;


import com.vsevolodvisnevskij.data.entity.Datum;
import com.vsevolodvisnevskij.data.entity.Root;
import com.vsevolodvisnevskij.domain.entity.Gif;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

@Singleton
public class RestService {
    private RestApi restApi;

    @Inject
    public RestService(RestApi restApi) {
        this.restApi = restApi;
    }


    public Observable<List<Gif>> loadTrending(String key, String offset) {
        return restApi.getTrending(key, offset).map(new Function<Root, List<Gif>>() {
            @Override
            public List<Gif> apply(Root root) throws Exception {
                List<Gif> gifs = new ArrayList<>();
                for (Datum d : root.getData()) {
                    Gif gif = new Gif();
                    gif.setUrl(d.getImages().getPreviewGif().getUrl());
                    gifs.add(gif);
                }
                return gifs;
            }
        });
    }

    public Observable<List<Gif>> loadSearch(String key, String search, String offset) {
        return restApi.getSearch(key, search, offset).map(new Function<Root, List<Gif>>() {
            @Override
            public List<Gif> apply(Root root) {
                List<Gif> gifs = new ArrayList<>();
                for (Datum d : root.getData()) {
                    Gif gif = new Gif();
                    gif.setUrl(d.getImages().getPreviewGif().getUrl());
                    gifs.add(gif);
                }
                return gifs;
            }
        });
    }
}
