package io.pivotal.beach.osusume.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.fragments.NewRestaurantFragment;
import io.pivotal.beach.osusume.android.models.NewRestaurant;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.models.RestaurantWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRestaurantActivity extends AppCompatActivity {

    @Inject
    OsusumeApiClient osusumeApiClient;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private NewRestaurantFragment newRestaurantFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);
        ((OsusumeApplication) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        newRestaurantFragment = (NewRestaurantFragment) getSupportFragmentManager()
                .findFragmentById(R.id.newRestaurantFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.createRestaurant) {
            NewRestaurant newRestaurant = newRestaurantFragment.getNewRestaurant();

            osusumeApiClient.postRestaurant(new RestaurantWrapper(newRestaurant))
                    .enqueue(new Callback<Restaurant>() {
                        @Override
                        public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                            if (response.code() == 201) {
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Restaurant> call, Throwable t) { }
                    });
        }

        return super.onOptionsItemSelected(item);
    }
}
