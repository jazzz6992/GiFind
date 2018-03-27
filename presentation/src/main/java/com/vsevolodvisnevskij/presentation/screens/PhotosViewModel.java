package com.vsevolodvisnevskij.presentation.screens;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.interactors.GetSearchGifsUseCase;
import com.vsevolodvisnevskij.domain.interactors.GetTrandingGifsUseCase;
import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.GifItemBinding;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;
import com.vsevolodvisnevskij.presentation.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class PhotosViewModel extends BaseViewModel {


    @Inject
    public GetTrandingGifsUseCase getTrandingGifsUseCase;
    @Inject
    public GetSearchGifsUseCase getSearchGifsUseCase;

    private GifAdapter adapter = new GifAdapter();

    private String search;

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }


    class GifHolder extends RecyclerView.ViewHolder {
        public GifItemBinding binding;

        public GifHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.setViewModel(new ItemGifViewModel());
        }
    }

    public class GifAdapter extends RecyclerView.Adapter<GifHolder> {
        private List<Gif> gifs = new ArrayList<>();

        public void addUserEntities(List<Gif> gifList) {
            this.gifs.addAll(gifList);
        }

        @Override
        public GifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            GifItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.gif_item, parent, false);
            return new GifHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(GifHolder holder, int position) {
            holder.binding.getViewModel().setEntity(gifs.get(position));
            if (position == gifs.size() - 1) {
                getNextGifs(String.valueOf(position));
            }
        }

        @Override
        public int getItemCount() {
            return gifs.size();
        }

        public void clear() {
            gifs.clear();
        }

        public void update(List<Gif> gifs) {
            if (gifs.size() != 0) {
                adapter.addUserEntities(gifs);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public GifAdapter getAdapter() {
        return adapter;
    }

    public PhotosViewModel() {
        getNextTranding("0");
    }

    private void getNextSearch(String q, String offset) {
        getSearchGifsUseCase.get(Constants.KEY, q, offset).subscribe(new Observer<List<Gif>>() {
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
        getTrandingGifsUseCase.get(Constants.KEY, offset).subscribe(new Observer<List<Gif>>() {
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
