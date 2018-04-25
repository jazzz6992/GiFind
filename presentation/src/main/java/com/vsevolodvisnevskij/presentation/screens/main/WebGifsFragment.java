package com.vsevolodvisnevskij.presentation.screens.main;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.FragmentWebGifsBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMFragment;


public class WebGifsFragment extends BaseMVVMFragment<FragmentWebGifsBinding, WebGifsViewModel, MainRouter> {

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_web_gifs;
    }

    @Override
    public WebGifsViewModel provideViewModel() {
        return ViewModelProviders.of(this).get(WebGifsViewModel.class);
    }

    @Override
    public MainRouter provideRouter() {
        return new MainRouter(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
    }

    private void search(String key) {
        binding.getViewModel().search(key);
    }

}
