package com.vsevolodvisnevskij.presentation.screens.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.ActivityMainBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMActivity;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding, PhotosViewModel, MainRouter> {

    @Override
    public int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public PhotosViewModel provideViewModel() {
        return ViewModelProviders.of(this).get(PhotosViewModel.class);
    }

    @Override
    public MainRouter provideRouter() {
        return new MainRouter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String key) {
        binding.getViewModel().search(key);
    }
}
