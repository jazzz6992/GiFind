package com.vsevolodvisnevskij.presentation.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vsevolodvisnevskij.giphy.BR;


/**
 * Created by vsevolodvisnevskij on 12.03.2018.
 */

public abstract class BaseMVVMActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel, R extends Router> extends AppCompatActivity {
    protected Binding binding;
    protected ViewModel viewModel;
    protected R router;

    public abstract int provideLayoutId();

    public abstract ViewModel provideViewModel();

    public abstract R provideRouter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, provideLayoutId());
        viewModel = provideViewModel();
        binding.setVariable(BR.viewModel, viewModel);
        router = provideRouter();
        viewModel.attachRouter(router);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        router = null;
        viewModel.detachRouter();
    }
}
