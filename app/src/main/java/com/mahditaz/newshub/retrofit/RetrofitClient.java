package com.mahditaz.newshub.retrofit;

import static com.mahditaz.newshub.util.Utils.BASE_URL;

import com.mahditaz.newshub.model.Results;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static ApiService getService(){
        if (retrofit == null) retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        return retrofit.create(ApiService.class);
    }
    public interface ApiService{
        @GET("top-headlines")
        Single<Results> getTopHeadlinesByCategoryPagePageSizeSortAndLanguage(@Query("apiKey") String apiKey, @Query("category") String category, @Query("page") int page, @Query("pageSize") int pageSize, @Query("language") String language, @Query("sortBy") String sortBy);

        @GET("everything")
        Single<Results> getEverythingBySearchPagePageSizeSortAndLanguage(@Query("apiKey") String apiKey, @Query("q") String q, @Query("page") int page, @Query("pageSize") int pageSize, @Query("sortBy") String sortBy, @Query("language") String language);
    }
}
