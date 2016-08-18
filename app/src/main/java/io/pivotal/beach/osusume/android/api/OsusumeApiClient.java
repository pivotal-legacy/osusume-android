package io.pivotal.beach.osusume.android.api;

import java.util.List;

import io.pivotal.beach.osusume.android.models.Login;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.models.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OsusumeApiClient {
    @POST("/session")
    Call<Token> login(@Body Login login);

    @GET("/restaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("/restaurants/{id}")
    Call<Restaurant> getRestaurant(@Path("id") Integer id);

    @POST("/restaurants")
    Call<Restaurant> postRestaurant(@Body Restaurant restaurant);
}