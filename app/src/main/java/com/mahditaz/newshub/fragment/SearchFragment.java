package com.mahditaz.newshub.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.mahditaz.newshub.R;
import com.mahditaz.newshub.adapter.ArticlesLoadStateAdapter;
import com.mahditaz.newshub.adapter.ArticlesAdapter;
import com.mahditaz.newshub.databinding.FragmentSearchBinding;
import com.mahditaz.newshub.util.ArticleComparator;
import com.mahditaz.newshub.util.Utils;
import com.mahditaz.newshub.viewmodel.SearchViewModel;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    ArticlesAdapter adapter;
    SearchViewModel viewModel;
    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()) Toast.makeText(getContext(), "Error in API key!", Toast.LENGTH_SHORT).show();

        adapter = new ArticlesAdapter(new ArticleComparator(), getGlide(getContext()), getContext());
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        initRVandAdapter();

        viewModel.articlePagingDataFlowable.subscribe(moviePagingData -> adapter.submitData(getLifecycle(), moviePagingData));
    }


    private void initRVandAdapter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.searchRV.setLayoutManager(gridLayoutManager);
        binding.searchRV.setAdapter(adapter.withLoadStateFooter(
                new ArticlesLoadStateAdapter(view -> adapter.retry())
        ));
    }

    public RequestManager getGlide(@ApplicationContext Context context){
        return Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .error(R.drawable.baseline_downloading_24)
                        .placeholder(R.drawable.baseline_downloading_24));
    }
}