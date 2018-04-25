package com.vsevolodvisnevskij.presentation.binding;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by vsevolodvisnevskij on 22.03.2018.
 */

public class BindingAdapters {

    @BindingAdapter({"adapter"})
    public static void initRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"pager_adapter"})
    public static void initViewPager(ViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter({"src"})
    public static void loadImage(ImageView view, String url) {
        if (url == null)
            return;
        Glide.with(view.getContext()).load(url).into(view);
    }
}
