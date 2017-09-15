package com.chrisowen.purepointapp;

import com.chrisowen.purepointapp.network.RecipePuppyService;
import com.chrisowen.purepointapp.pojo.Recipe;
import com.chrisowen.purepointapp.pojo.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSource {

    private int MAX_CACHE_SIZE = 10;

    private Gson gson;
    private Retrofit retrofit;
    private static LinkedHashMap<String, Result> cache = new LinkedHashMap<>();
    private RecipePuppyService recipePuppyService;

    public DataSource(){
        gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit
            .Builder()
            .client(httpClient.build())
            .baseUrl(RecipePuppyService.SERVICE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }

    public Observable<List<Recipe>> searchRecipe(final String searchRecipe){
        if(cache.containsKey(searchRecipe)){
            return Observable.fromArray(cache.get(searchRecipe).getRecipes()).take(20);
        }

        limitCacheSize();

        recipePuppyService = retrofit.create(RecipePuppyService.class);

        return recipePuppyService
                .searchRecipes(searchRecipe)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> {
                    cache.put(searchRecipe, result);
                    return result.getRecipes();
                })
                .take(20);
    }

    private void limitCacheSize(){
        if(cache.size() > MAX_CACHE_SIZE){
            List<String> list = new ArrayList<>(cache.keySet());
            cache.remove(list.get(0));
        }
    }

}
