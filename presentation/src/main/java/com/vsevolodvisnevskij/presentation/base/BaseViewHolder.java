package com.vsevolodvisnevskij.presentation.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.vsevolodvisnevskij.giphy.BR;

public abstract class BaseViewHolder<Model, ViewModel extends BaseItemViewModel<Model>, DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private DataBinding binding;
    private ViewModel viewModel;

    public BaseViewHolder(DataBinding binding, ViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        initViewModel();
    }

    private void initViewModel() {
        binding.setVariable(BR.viewModel, viewModel);
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void bind(Model model) {
        viewModel.setItem(model);
        binding.executePendingBindings();
    }
}
