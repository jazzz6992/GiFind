package com.vsevolodvisnevskij.presentation.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.vsevolodvisnevskij.domain.interactors.DownloadGifUseCase;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class SingleGifViewModel extends BaseViewModel<DetailRouter> {
    private ObservableField<String> gifUrl = new ObservableField<>();
    @Inject
    public Context context;
    @Inject
    public DownloadGifUseCase downloadGifUseCase;

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }

    public String getGifUrl() {
        return gifUrl.get();
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl.set(gifUrl);
    }

    public void share() {
        compositeDisposable.add(downloadGifUseCase.download(gifUrl.get()).subscribe(f -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/gif");
            Uri uri = FileProvider.getUriForFile(context, "com.vsevolodvisnevskij.presentation.fileprovider", f);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            List<ResolveInfo> cameraActivities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo activity : cameraActivities) {
                context.grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }));
    }
}
