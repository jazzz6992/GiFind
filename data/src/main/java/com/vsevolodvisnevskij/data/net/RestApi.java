package com.vsevolodvisnevskij.data.net;


import com.vsevolodvisnevskij.data.entity.Root;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vsevolodvisnevskij on 19.03.2018.
 */

public interface RestApi {
    @GET("v1/gifs/trending")
    Observable<Root> getTrending(@Query("api_key") String key, @Query("offset") String offset);

    @GET("v1/gifs/search")
    Observable<Root> getSearch(@Query("api_key") String key, @Query("q") String search, @Query("offset") String offset);
}
