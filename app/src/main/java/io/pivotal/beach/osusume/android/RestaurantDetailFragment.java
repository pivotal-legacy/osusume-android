package io.pivotal.beach.osusume.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.models.Restaurant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantDetailFragment extends Fragment {
    public static String TAG = RestaurantDetailFragment.class.getName();

    private static String FRAGMENT_RESTAURANT_ID = "fragmentRestaurantId";

    TextView restaurantNameView;

    Restaurant restaurant;

    public static RestaurantDetailFragment newInstance(int restaurantId) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();

        Bundle args = new Bundle();
        args.putInt(FRAGMENT_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);

        return fragment;
    }

    public int getRetaurantId() {
        return getArguments().getInt(FRAGMENT_RESTAURANT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        restaurantNameView = (TextView) rootView.findViewById(R.id.restaurantDetailsName);

        updateRestaurant();

        return rootView;
    }
    
    private static String BASE_URL = "http://osusume.cfapps.io";

    private void updateRestaurant() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        OsusumeApiClient apiClient = retrofit.create(OsusumeApiClient.class);

        Call<Restaurant> call = apiClient.getRestaurant(getRetaurantId());
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                restaurant = response.body();
                restaurantNameView.setText(restaurant.getName());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
            }
        });
    }



}
