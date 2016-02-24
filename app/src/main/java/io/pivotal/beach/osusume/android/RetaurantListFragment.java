package io.pivotal.beach.osusume.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.presenters.RestaurantPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class RetaurantListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Restaurant> restaurantList;

    @Inject
    OsusumeApiClient osusumeApiClient;

    public RetaurantListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ((OsusumeApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restaurantList = new ArrayList<Restaurant>();
        adapter = new RestaurantAdapter(restaurantList);

        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantListView);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        updateRestaurants();

        return rootView;
    }

    private void updateRestaurants() {
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
            public TextView restaurantNameView;
            public TextView restaurantAuthorView;
            public TextView restaurantCreatedAtView;

            public ViewHolder(View containerView, TextView restaurantNameView, TextView restaurantAuthorView, TextView restaurantCreatedAtView) {
                super(containerView);
                this.restaurantNameView = restaurantNameView;
                this.restaurantAuthorView = restaurantAuthorView;
                this.restaurantCreatedAtView = restaurantCreatedAtView;

                containerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Restaurant restaurant = restaurants.get(ViewHolder.this.getAdapterPosition());
                        int id = restaurant.getId();

                        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(id);
                        FragmentManager fragmentManager = RetaurantListFragment.this.getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment, detailFragment, RestaurantDetailFragment.TAG)
                                .addToBackStack(null)
                                .commit();
                    }
                });
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