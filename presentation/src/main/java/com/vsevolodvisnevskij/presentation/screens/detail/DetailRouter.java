package com.vsevolodvisnevskij.presentation.screens.detail;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.vsevolodvisnevskij.presentation.base.Router;

import java.io.File;
import java.util.List;

public class DetailRouter extends Router {
    public DetailRouter(Activity activity) {
        super(activity);
    }

    public void navigateToActivityChooser(File f) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/gif");
        Uri uri = FileProvider.getUriForFile(getActivity(), "com.vsevolodvisnevskij.presentation.fileprovider", f);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        List<ResolveInfo> imageActivities = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : imageActivities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
