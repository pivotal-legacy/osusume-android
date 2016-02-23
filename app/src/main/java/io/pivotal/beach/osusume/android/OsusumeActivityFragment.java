package io.pivotal.beach.osusume.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.presenters.RestaurantPresenter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class OsusumeActivityFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Restaurant> restaurantList;

    public OsusumeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restaurantList = new ArrayList<Restaurant>();
        adapter = new RestaurantAdapter(restaurantList);

        View rootView = inflater.inflate(R.layout.fragment_osusume, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantListView);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        updateRestaurants();

        return rootView;
    }

    private static String BASE_URL = "http://osusume.cfapps.io";

    private void updateRestaurants() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        OsusumeApiClient apiClient = retrofit.create(OsusumeApiClient.class);

        Call<List<Restaurant>> call = apiClient.getRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                restaurantList.clear();
                restaurantList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
            }
        });
    }

    class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
        List<Restaurant> restaurants;

        public RestaurantAdapter(List<Restaurant> restaurants) {
            this.restaurants = restaurants;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView restaurantNameView;
            public TextView restaurantAuthorView;
            public TextView restaurantCreatedAtView;

            public ViewHolder(View containerView, TextView restaurantCreatedAtView, TextView restaurantAuthorView, TextView restaurantNameView) {
                super(containerView);
                this.restaurantCreatedAtView = restaurantCreatedAtView;
                this.restaurantAuthorView = restaurantAuthorView;
                this.restaurantNameView = restaurantNameView;
            }
        }

        @Override
        public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_restaurant, parent, false);

            TextView restaurantNameView = (TextView) view.findViewById(R.id.restaurantName);
            TextView restaurantAuthorView = (TextView) view.findViewById(R.id.restaurantAuthor);
            TextView restaurantCreatedAtView = (TextView) view.findViewById(R.id.restaurantCreatedAt);

            return new ViewHolder(view, restaurantNameView, restaurantAuthorView, restaurantCreatedAtView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RestaurantPresenter restaurantPresenter = new RestaurantPresenter(restaurants.get(position));

            holder.restaurantNameView.setText(restaurantPresenter.getName());
            holder.restaurantAuthorView.setText(restaurantPresenter.getAuthorName());
            holder.restaurantCreatedAtView.setText(restaurantPresenter.getCreatedAtInText());
        }

        @Override
        public int getItemCount() {
            return restaurants.size();
        }
    }
}