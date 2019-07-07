package com.vsevolodvisnevskij.presentation.screens.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.interactors.GetTrandingGifsUseCase;
import com.vsevolodvisnevskij.domain.interactors.SearchGifsUseCase;
import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.GifItemBinding;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseAdapter;
import com.vsevolodvisnevskij.presentation.base.BaseViewHolder;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class WebGifsViewModel extends BaseViewModel<MainRouter> {


    @Inject
    Context context;
    @Inject
    public GetTrandingGifsUseCase getTrandingGifsUseCase;
    @Inject
    public SearchGifsUseCase searchGifsUseCase;

    private GifAdapter adapter = new GifAdapter();
    //todo создать publishSubject который будет высылать boolean - состояние подключения
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (isOnline(context)) {
                isConnected.set(true);
            }
        }
    };

    public ObservableBoolean isConnected = new ObservableBoolean(true);
    private String search;
    private int offset = 0;

    public WebGifsViewModel() {
        compositeDisposable.add(adapter.getClickSubject().subscribe(g -> {
            if (router != null) {
                router.navigateToDetailActivity(g);
            }
        }));
        getNextGifs(String.valueOf(offset));
    }

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }


    class GifHolder extends BaseViewHolder<Gif, ItemLinkGifViewModel, GifItemBinding> {
        public GifItemBinding binding;


        public GifHolder(GifItemBinding binding, ItemLinkGifViewModel viewModel) {
            super(binding, viewModel);
        }
    }

    public class GifAdapter extends BaseAdapter<Gif, ItemLinkGifViewModel> {


        @Override
        public GifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            GifItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.gif_item, parent, false);
            return new GifHolder(binding, new ItemLinkGifViewModel());
        }

        @Override
        public void onBindViewHolder(BaseViewHolder<Gif, ItemLinkGifViewModel, ?> holder, int position) {
            super.onBindViewHolder(holder, position);
            if (position == items.size() - 1) {
                getNextGifs(String.valueOf(offset));
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clear() {
            items.clear();
        }

        public void update(List<Gif> gifs) {
            if (gifs.size() != 0) {
                offset += gifs.size();
                List<Gif> gifs1 = new ArrayList<>();
                for (Gif g : gifs) {
                    if (g.getPreviewUrl() != null) {
                        gifs1.add(g);
                    }
                }
                adapter.addItems(gifs1);
            }
        }
    }

    public GifAdapter getAdapter() {
        return adapter;
    }

    private void getNextSearch(String q, String offset) {
        compositeDisposable.add(searchGifsUseCase.get(q, offset).subscribe(l -> adapter.update(l), this::handleError));
    }

    private void getNextTranding(String offset) {
        compositeDisposable.add(getTrandingGifsUseCase.get(offset).subscribe(l -> adapter.update(l), this::handleError));
    }

    public void search(String search) {
        this.search = search;
        adapter.clear();
        offset = 0;
        getNextGifs(String.valueOf(offset));
    }

    public void getNextGifs(String offset) {
        if (search == null || search.length() == 0) {
            getNextTranding(offset);
        } else {
            getNextSearch(search, offset);
        }
    }

    @Override
    public void handleError(Throwable e) {
        if (e instanceof IOException) {
            if (!isOnline(context)) {
                isConnected.set(false);
                errorMessage.set(context.getString(R.string.connection_problems));
            }
        } else {
            errorMessage.set(context.getString(R.string.unknown_error));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        context.unregisterReceiver(receiver);
    }
}
