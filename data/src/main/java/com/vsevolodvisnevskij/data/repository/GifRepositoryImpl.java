package com.vsevolodvisnevskij.data.repository;

import android.content.Context;

import com.vsevolodvisnevskij.data.constants.Constants;
import com.vsevolodvisnevskij.data.net.RestService;
import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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

    @Override
    public Observable<File> download(String link) {
        Observable<File> observable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                String urlSpec = link;
                File file = new File(context.getFilesDir(), "tmp.gif");
                URL url = null;
                try {
                    url = new URL(urlSpec);
                    HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                    try {
                        InputStream in = httpURLConnection.getInputStream();
                        if (httpURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                            throw new IOException(httpURLConnection.getResponseMessage() + ": with " + urlSpec);
                        }
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            int bufSize;
                            byte[] buf = new byte[4096];
                            while ((bufSize = in.read(buf)) > 0) {
                                fos.write(buf, 0, bufSize);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        httpURLConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                emitter.onNext(file);
            }
        });
        return observable;
    }
}
