package io.pivotal.beach.osusume.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.activities.NewRestaurantActivity;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.presenters.RestaurantPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListFragment extends ApiFragment {
    List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    RecyclerView.Adapter adapter = new RestaurantAdapter(restaurantList);

    @Bind(R.id.restaurantListView)
    RecyclerView recyclerView;

    @Bind(R.id.addRestaurantButton)
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRestaurants();
    }

    @OnClick(R.id.addRestaurantButton)
    public void onAddRestaurantButtonClicked(View view) {
        Intent intent = new Intent(getActivity(), NewRestaurantActivity.class);
        getActivity().startActivity(intent);
    }

    void updateRestaurants() {
        osusumeApiClient.getRestaurants().enqueue(new Callback<List<Restaurant>>() {
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

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.restaurantName)
            public TextView restaurantNameView;

            @Bind(R.id.restaurantAuthor)
            public TextView restaurantAuthorView;

            @Bind(R.id.restaurantCreatedAt)
            public TextView restaurantCreatedAtView;

            public ViewHolder(View containerView) {
                super(containerView);
                ButterKnife.bind(this, containerView);
            }

            @OnClick(R.id.listItemRestaurantView)
            void onClick(View v) {
                Restaurant restaurant = restaurants.get(getAdapterPosition());
                int id = restaurant.getId();

                RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(id);
                FragmentManager fragmentManager = RestaurantListFragment.this.getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.restaurantListFragment, detailFragment, RestaurantDetailFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        }

        @Override
        public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_restaurant, parent, false);
            return new ViewHolder(view);
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