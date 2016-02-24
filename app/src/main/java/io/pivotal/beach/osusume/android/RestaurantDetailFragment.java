package io.pivotal.beach.osusume.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.pivotal.beach.osusume.android.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailFragment extends ApiFragment {
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

    private void updateRestaurant() {
        osusumeApiClient.getRestaurant(getRetaurantId()).enqueue(new Callback<Restaurant>() {
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
