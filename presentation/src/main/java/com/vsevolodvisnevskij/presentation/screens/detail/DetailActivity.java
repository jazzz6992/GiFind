package com.vsevolodvisnevskij.presentation.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.ActivityDetailBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DetailActivity extends BaseMVVMActivity<ActivityDetailBinding, SingleGifViewModel, DetailRouter> {


    private static final String EXTRA_URL = "URL";

    @Override
    public int provideLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public SingleGifViewModel provideViewModel() {
        SingleGifViewModel singleGifViewModel = new SingleGifViewModel();
        singleGifViewModel.setGifUrl(getIntent().getStringExtra(EXTRA_URL));
        return singleGifViewModel;
    }

    @Override
    public DetailRouter provideRouter() {
        return new DetailRouter(this);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_gif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                viewModel.share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}