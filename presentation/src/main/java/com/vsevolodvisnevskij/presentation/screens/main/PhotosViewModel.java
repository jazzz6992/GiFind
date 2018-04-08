package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.DataBindingUtil;
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

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class PhotosViewModel extends BaseViewModel<MainRouter> {


    @Inject
    public GetTrandingGifsUseCase getTrandingGifsUseCase;
    @Inject
    public SearchGifsUseCase SearchGifsUseCase;

    private GifAdapter adapter = new GifAdapter();

    private String search;

    public PhotosViewModel() {
        compositeDisposable.add(adapter.getClickSubject().subscribe(g -> {
            router.navigateToDetailActivity(g.getOriginalUrl());
        }));
        getNextGifs("0");
    }

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }


    class GifHolder extends BaseViewHolder<Gif, ItemGifViewModel, GifItemBinding> {
        public GifItemBinding binding;


        public GifHolder(GifItemBinding binding, ItemGifViewModel viewModel) {
            super(binding, viewModel);
        }
    }

    public class GifAdapter extends BaseAdapter<Gif, ItemGifViewModel> {


        @Override
        public GifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            GifItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.gif_item, parent, false);
            return new GifHolder(binding, new ItemGifViewModel());
        }

        @Override
        public void onBindViewHolder(BaseViewHolder<Gif, ItemGifViewModel, ?> holder, int position) {
            super.onBindViewHolder(holder, position);
            if (position == items.size() - 1) {
                getNextGifs(String.valueOf(position));
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
                adapter.addItems(gifs);
            }
        }
    }

    public GifAdapter getAdapter() {
        return adapter;
    }


    private void getNextSearch(String q, String offset) {
        SearchGifsUseCase.get(q, offset).subscribe(new Observer<List<Gif>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Gif> gifs) {
                adapter.update(gifs);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getNextTranding(String offset) {
        getTrandingGifsUseCase.get(offset).subscribe(new Observer<List<Gif>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Gif> gifs) {
                adapter.update(gifs);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void search(String search) {
        this.search = search;
        adapter.clear();
        getNextGifs("0");
    }

    public void getNextGifs(String offset) {
        if (search == null || search.length() == 0) {
            getNextTranding(offset);
        } else {
            getNextSearch(search, offset);
        }
    }
}
