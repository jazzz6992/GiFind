package com.vsevolodvisnevskij.presentation.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public abstract class BaseAdapter<Model, ViewModel extends BaseItemViewModel<Model>> extends RecyclerView.Adapter<BaseViewHolder<Model, ViewModel, ?>> {
    protected List<Model> items = new ArrayList<>();
    private PublishSubject<Model> clickSubject = PublishSubject.create();

    public PublishSubject<Model> getClickSubject() {
        return clickSubject;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<Model, ViewModel, ?> holder, int position) {
        Model model = items.get(position);
        holder.bind(model);
    }

    public void setItems(List<Model> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Model> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder<Model, ViewModel, ?> holder) {
        super.onViewAttachedToWindow(holder);
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (items.size() > pos) {
                clickSubject.onNext(items.get(pos));
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder<Model, ViewModel, ?> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.setOnClickListener(null);
    }
}
