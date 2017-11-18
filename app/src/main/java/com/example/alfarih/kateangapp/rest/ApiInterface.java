package com.example.alfarih.kateangapp.rest;

import com.example.alfarih.kateangapp.model.explore.Explore;
import com.example.alfarih.kateangapp.model.photos.Photos;
import com.example.alfarih.kateangapp.model.trending.Trending;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alfarih on 13/11/17.
 */

public interface ApiInterface {
    @GET ("venues/explore/")
    Call<Explore> getRecomendationNear(@Query("client_id")String client_id,
                                   @Query("client_secret") String client_secret,
                                   @Query("v") String versioning,
                                   @Query("ll") String latlng,
                                   @Query("sortByDistance") boolean sortByDistance);
    @GET ("venues/explore/")
    Call<Trending> getTrending(@Query("client_id")String client_id,
                                    @Query("client_secret") String client_secret,
                                    @Query("v") String versioning,
                                    @Query("ll") String latlng);

    @GET ("venues/{venues_id}/photos/")
    Call<Photos> getPhotos(@Path("venues_id") String venues_id,
                           @Query("client_id")String client_id,
                           @Query("client_secret") String client_secret,
                           @Query("v") String versioning);


}
