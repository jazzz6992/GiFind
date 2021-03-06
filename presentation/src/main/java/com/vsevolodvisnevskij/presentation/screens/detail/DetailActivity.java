package com.vsevolodvisnevskij.presentation.screens.detail;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.ActivityDetailBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMActivity;

import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends BaseMVVMActivity<ActivityDetailBinding, SingleGifViewModel, DetailRouter> {


    private static final String EXTRA_ID = "ID";
    private static final String EXTRA_PREVIEW = "PREVIEW";
    private static final String EXTRA_ORIG = "ORIG";
    private static final String EXTRA_PATH = "PATH";
    private static final String EXTRA_LOCAL = "LOCAL";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public int provideLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public SingleGifViewModel provideViewModel() {
        SingleGifViewModel singleGifViewModel = ViewModelProviders.of(this).get(SingleGifViewModel.class);
        Intent intent = getIntent();
        boolean local = intent.getBooleanExtra(EXTRA_LOCAL, false);
        singleGifViewModel.setLocal(local);
        Gif gif = new Gif();
        gif.setId(intent.getStringExtra(EXTRA_ID));
        if (local) {
            gif.setPath(intent.getStringExtra(EXTRA_PATH));
        } else {
            gif.setOriginalUrl(intent.getStringExtra(EXTRA_ORIG));
            gif.setPreviewUrl(intent.getStringExtra(EXTRA_PREVIEW));
        }
        singleGifViewModel.setGif(gif);
        return singleGifViewModel;
    }

    @Override
    public DetailRouter provideRouter() {
        return new DetailRouter(this);
    }

    public static Intent newIntent(Context context, Gif g) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ID, g.getId());
        intent.putExtra(EXTRA_PREVIEW, g.getPreviewUrl());
        intent.putExtra(EXTRA_ORIG, g.getOriginalUrl());
        intent.putExtra(EXTRA_PATH, g.getPath());
        intent.putExtra(EXTRA_LOCAL, g.getPath() != null);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_gif, menu);
        MenuItem item = menu.findItem(R.id.favorite);
        compositeDisposable.add(viewModel.checkFavorite().subscribe(b -> {
            if (b) {
                item.setIcon(R.drawable.ic_favorite_24dp);
            } else {
                item.setIcon(R.drawable.ic_favorite_border_24dp);
            }
        }));
        compositeDisposable.add(viewModel.getPublishSubject().subscribe(completable -> {
            compositeDisposable.add(completable.subscribe(() -> item.setEnabled(true), e -> item.setEnabled(true)));
        }));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                viewModel.share();
                return true;
            case R.id.favorite:
                viewModel.toFromFavorites();
                item.setEnabled(false);
                return true;
            case R.id.download:
                RxPermissions rxPermissions = new RxPermissions(this);
                compositeDisposable.add(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(b -> {
                    if (b) {
                        viewModel.download();
                    }
                }));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}