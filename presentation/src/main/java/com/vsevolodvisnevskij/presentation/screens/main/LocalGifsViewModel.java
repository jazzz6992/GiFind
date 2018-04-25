package com.vsevolodvisnevskij.presentation.screens.main;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.domain.interactors.GetLocalGifsUseCase;
import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.PathItemBinding;
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


    class PathHolder extends BaseViewHolder<String, ItemPathGifViewModel, PathItemBinding> {
        public PathItemBinding binding;


        public PathHolder(PathItemBinding binding, ItemPathGifViewModel viewModel) {
            super(binding, viewModel);
        }
    }

    public class PathAdapter extends BaseAdapter<String, ItemPathGifViewModel> {


        @Override
        public PathHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            PathItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.path_item, parent, false);
            return new PathHolder(binding, new ItemPathGifViewModel());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void update(List<String> paths) {
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
