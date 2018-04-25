package com.vsevolodvisnevskij.presentation.screens.main;


import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsevolodvisnevskij.giphy.R;
import com.vsevolodvisnevskij.giphy.databinding.FragmentLocalGifsBinding;
import com.vsevolodvisnevskij.presentation.base.BaseMVVMFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalGifsFragment extends BaseMVVMFragment<FragmentLocalGifsBinding, LocalGifsViewModel, MainRouter> {


    @Override
    public int provideLayoutId() {
        return R.layout.fragment_local_gifs;
    }

    @Override
    public LocalGifsViewModel provideViewModel() {
        return ViewModelProviders.of(this).get(LocalGifsViewModel.class);
    }

    @Override
    public MainRouter provideRouter() {
        return new MainRouter(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

}
