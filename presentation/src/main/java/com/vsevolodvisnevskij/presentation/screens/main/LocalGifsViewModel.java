package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.domain.entity.Gif;
import com.vsevolodvisnevskij.domain.interactors.GetLocalGifsUseCase;
import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.GifItemBinding;
import com.vsevolodvisnevskij.presentation.app.App;
import com.vsevolodvisnevskij.presentation.base.BaseAdapter;
import com.vsevolodvisnevskij.presentation.base.BaseViewHolder;
import com.vsevolodvisnevskij.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class LocalGifsViewModel extends BaseViewModel<MainRouter> {
    @Inject
    public GetLocalGifsUseCase getLocalGifsUseCase;

    @Override
    public void createInject() {
        App.getAppComponent().inject(this);
    }


    private PathAdapter adapter = new PathAdapter();


    public LocalGifsViewModel() {
        compositeDisposable.add(adapter.getClickSubject().subscribe(g -> {
            if (router != null) {
                router.navigateToDetailActivity(g);
            }
        }));
        getNextGifs();
    }


    class PathHolder extends BaseViewHolder<Gif, ItemPathGifViewModel, GifItemBinding> {
        public GifItemBinding binding;


        public PathHolder(GifItemBinding binding, ItemPathGifViewModel viewModel) {
            super(binding, viewModel);
        }
    }

    public class PathAdapter extends BaseAdapter<Gif, ItemPathGifViewModel> {


        @Override
        public PathHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            GifItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.gif_item, parent, false);
            return new PathHolder(binding, new ItemPathGifViewModel());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void update(List<Gif> paths) {
            adapter.setItems(paths);
        }
    }

    public PathAdapter getAdapter() {
        return adapter;
    }

    public void getNextGifs() {
        compositeDisposable.add(getLocalGifsUseCase.getLocalGifs().subscribe(l -> adapter.update(l)));
    }
}
