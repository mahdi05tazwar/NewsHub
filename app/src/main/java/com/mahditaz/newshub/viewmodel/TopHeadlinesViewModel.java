package com.mahditaz.newshub.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.mahditaz.newshub.model.Article;
import com.mahditaz.newshub.paging.TopHeadlinesPagingSource;
import com.mahditaz.newshub.util.Utils;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class TopHeadlinesViewModel extends ViewModel {
    public Flowable<PagingData<Article>> articlePagingDataFlowable;

    public TopHeadlinesViewModel(){init();}

    private void init(){
        TopHeadlinesPagingSource pagingSource = new TopHeadlinesPagingSource();
        Pager<Integer, Article> pager = new Pager(new PagingConfig(
                Utils.pageSize,
                Utils.pageSize,
                false,
                Utils.pageSize,
                Utils.pageSize * 5
        ), () -> pagingSource);

        articlePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);

        PagingRx.cachedIn(articlePagingDataFlowable, coroutineScope);
    }
}
