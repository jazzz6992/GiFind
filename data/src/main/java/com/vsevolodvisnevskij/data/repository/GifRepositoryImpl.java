package com.vsevolodvisnevskij.data.repository;

import android.content.Context;

import com.vsevolodvisnevskij.data.constants.Constants;
import com.vsevolodvisnevskij.data.db.AppDatabase;
import com.vsevolodvisnevskij.data.db.GifDao;
import com.vsevolodvisnevskij.data.entity.db.GifDB;
import com.vsevolodvisnevskij.data.net.RestService;
import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class GifRepositoryImpl implements GifRepository {
    private RestService restService;
    private Context context;
    private GifDao gifDao;

    @Inject
    public GifRepositoryImpl(Context context, RestService restService, AppDatabase appDatabase) {
        this.context = context;
        this.restService = restService;
        gifDao = appDatabase.getGifDao();
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
    public Observable<File> download(String link, String name) {
        return Observable.create(emitter -> {
            File file = new File(context.getFilesDir(), name);
            URL url = null;
            try {
                url = new URL(link);
                HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                try (InputStream in = httpURLConnection.getInputStream()) {
                    if (httpURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                        throw new IOException(httpURLConnection.getResponseMessage() + ": with " + link);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            emitter.onNext(file);
            emitter.onComplete();
        });
    }

    @Override
    public Completable addToFavorites(String id, String path) {
        return Completable.create(emitter -> {
            gifDao.insert(new GifDB(id, path));
            emitter.onComplete();
        });
    }

    @Override
    public Flowable<List<Gif>> getLocalGifs() {
        return gifDao.getAll().flatMap(l -> {
            List<Gif> list = new ArrayList<>();
            for (GifDB gifDB : l) {
                Gif g = new Gif();
                g.setId(gifDB.getId());
                g.setPath(gifDB.getPath());
                list.add(g);
            }
            return Flowable.fromArray(list);
        });
    }


    @Override
    public Flowable<Boolean> checkLocalGif(String id) {
        return gifDao.getById(id).flatMap(l -> {
            if (l != null && l.size() > 0) {
                return Flowable.just(true);
            } else {
                return Flowable.just(false);
            }
        });
    }

    @Override
    public Completable delete(String name) {
        return Completable.create(e -> {
            File file = new File(context.getFilesDir(), name);
            boolean delited = file.delete();
            if (delited) {
                e.onComplete();
            } else {
                e.onError(new IOException("Can't delete file " + file.getAbsolutePath()));
            }

        });
    }

    @Override
    public Completable removeFromFavorites(String id) {
        return Completable.create(e -> {
            gifDao.delete(id);
            e.onComplete();
        });
    }
}
