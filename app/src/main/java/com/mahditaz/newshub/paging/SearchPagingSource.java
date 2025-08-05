package com.mahditaz.newshub.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.mahditaz.newshub.model.Article;
import com.mahditaz.newshub.model.Results;
import com.mahditaz.newshub.retrofit.RetrofitClient;
import com.mahditaz.newshub.util.Utils;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPagingSource extends RxPagingSource<Integer, Article> {
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Article> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Article>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try{
            int page = loadParams.getKey() == null ? 1 : loadParams.getKey();

            return RetrofitClient.getService().
                    getEverythingBySearchPagePageSizeSortAndLanguage(Utils.API_KEY, Utils.currentQuery, page, Utils.pageSize, Utils.sortBy, Utils.articleLanguages[0])
                    .subscribeOn(Schedulers.io())
                    .map(Results::getArticles)
                    .map(articles -> toLoadResult(articles, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e){
            return Single.just(new LoadResult.Error(e));
        }
    }

    private LoadResult<Integer, Article> toLoadResult(List<Article> movies, int page){
        int positionOfFinalPage = Utils.numPages - 1;
        return new LoadResult.Page(movies,
                page == 1 ? null : page - 1,
                page == positionOfFinalPage ? null : page + 1);
    }
}
