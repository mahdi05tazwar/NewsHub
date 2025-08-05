package com.mahditaz.newshub.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.mahditaz.newshub.databinding.SingleArticleItemBinding;
import com.mahditaz.newshub.model.Article;

public class ArticlesAdapter extends PagingDataAdapter<Article, ArticlesAdapter.VH> {
    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;
    private Intent redirectIntent;
    RequestManager glide;
    Context context;
    public ArticlesAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback, RequestManager glide, Context context) {
        super(diffCallback);
        this.glide = glide;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticlesAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(SingleArticleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.VH holder, int position) {
        Article article = getItem(position);
        if (article != null) {
            glide.load(article.getUrlToImage()).into(holder.binding.imageView);
            holder.binding.textView.setText(article.getTitle());
            holder.binding.textView3.setText("By " + article.getAuthor() + " at " + article.getPublishedAt());

            holder.binding.getRoot().setOnClickListener(view -> {
                redirectIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                context.startActivity(redirectIntent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;
    }

    public class VH extends RecyclerView.ViewHolder{
        SingleArticleItemBinding binding;
        public VH(@NonNull SingleArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
