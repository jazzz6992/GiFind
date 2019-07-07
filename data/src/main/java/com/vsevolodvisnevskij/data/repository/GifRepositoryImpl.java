package com.vsevolodvisnevskij.data.repository;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.vsevolodvisnevskij.data.constants.Constants;
import com.vsevolodvisnevskij.data.db.AppDatabase;
import com.vsevolodvisnevskij.data.db.GifDao;
import com.vsevolodvisnevskij.data.entity.db.GifDB;
import com.vsevolodvisnevskij.data.net.RestService;
import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.repository.GifRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Emitter;
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
            url = new URL(link);
            boolean done = false;
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
                    done = true;
                }
            } finally {
                httpURLConnection.disconnect();
            }
            if (done) {
                emitter.onNext(file);
                emitter.onComplete();
            } else {
                emitter.onError(new IOException());
            }
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

    @Override
    public Completable saveFileToExternalStorage(String name, String path) {
        return Completable.create(e -> {
            File source = new File(path);
            File dest = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name);
            dest.createNewFile();
            try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos = new FileOutputStream(dest)) {
                byte[] buf = new byte[1024];
                int count;
                while ((count = fis.read(buf)) > 0) {
                    fos.write(buf, 0, count);
                }
            }
            MediaScannerConnection.scanFile(context,
                    new String[]{dest.toString()}, null, (p, uri) -> {
                        Log.i("ExternalStorage", "Scanned " + p + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    });
            e.onComplete();
        });
    }

    @Override
    public Flowable<Gif> getLocalGifById(String id) {
        return gifDao.getById(id).flatMap(l -> {
            if (l != null && l.size() > 0) {
                GifDB gifDB = l.get(0);
                Gif g = new Gif();
                g.setId(gifDB.getId());
                g.setPath(gifDB.getPath());
                return Flowable.just(g);
            } else {
                return Flowable.create(Emitter::onComplete, BackpressureStrategy.LATEST);
            }
        });
    }
}
