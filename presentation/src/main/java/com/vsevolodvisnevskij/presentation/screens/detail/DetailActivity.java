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
//                DownloadTask task = new DownloadTask();
//                task.execute(getIntent().getStringExtra(EXTRA_URL));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public class DownloadTask extends AsyncTask<String, Void, File> {
//
//        @Override
//        protected File doInBackground(String... strings) {
//            String urlSpec = getIntent().getStringExtra(EXTRA_URL);
//            File file = new File(getFilesDir(), "tmp.gif");
//            URL url = null;
//            try {
//                url = new URL(urlSpec);
//                HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
//                try {
//                    InputStream in = httpURLConnection.getInputStream();
//                    if (httpURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
//                        throw new IOException(httpURLConnection.getResponseMessage() + ": with " + urlSpec);
//                    }
//                    try (FileOutputStream fos = new FileOutputStream(file)) {
//                        int bufSize;
//                        byte[] buf = new byte[4096];
//                        while ((bufSize = in.read(buf)) > 0) {
//                            fos.write(buf, 0, bufSize);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    httpURLConnection.disconnect();
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return file;
//        }
//
//        @Override
//        protected void onPostExecute(File file) {
//            super.onPostExecute(file);
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("image/gif");
//            Uri uri = FileProvider.getUriForFile(DetailActivity.this, "com.vsevolodvisnevskij.presentation.fileprovider", file);
//            intent.putExtra(Intent.EXTRA_STREAM, uri);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            List<ResolveInfo> cameraActivities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            for (ResolveInfo activity : cameraActivities) {
//                grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
//            startActivity(intent);
//        }
//    }
}