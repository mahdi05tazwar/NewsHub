package com.mahditaz.newshub.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mahditaz.newshub.model.Article;

public class ArticleComparator extends DiffUtil.ItemCallback<Article> {
    @Override
    public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
        return oldItem.getUrl() == newItem.getUrl();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
        return oldItem.getUrl() == newItem.getUrl();
    }
}
