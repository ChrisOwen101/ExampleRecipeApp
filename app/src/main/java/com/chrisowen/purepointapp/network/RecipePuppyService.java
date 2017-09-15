package com.chrisowen.purepointapp.network;

import com.chrisowen.purepointapp.pojo.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipePuppyService {
    String SERVICE_ENDPOINT = "http://www.recipepuppy.com";

    @GET("/api/")
    Observable<Result> searchRecipes(@Query("q") String query);
}
