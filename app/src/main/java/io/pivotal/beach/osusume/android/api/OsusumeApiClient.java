package io.pivotal.beach.osusume.android.api;

import java.util.List;

import io.pivotal.beach.osusume.android.models.Restaurant;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OsusumeApiClient {
    @GET("/restaurants")
    Call<List<Restaurant>> getRestaurants();
}