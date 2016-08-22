package io.pivotal.beach.osusume.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.fragments.NewRestaurantFragment;
import io.pivotal.beach.osusume.android.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRestaurantActivity extends AppCompatActivity {

    @Inject
    OsusumeApiClient osusumeApiClient;

    EditText restaurantNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((OsusumeApplication) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_new_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restaurantNameField = (EditText) findViewById(R.id.newRestaurantNameField);
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
            String restaurantName = restaurantNameField.getText().toString();
            Restaurant restaurant = new Restaurant(restaurantName, null);

            osusumeApiClient.postRestaurant(restaurant).enqueue(new Callback<Restaurant>() {
                @Override
                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                    if (response.code() == 200) {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Restaurant> call, Throwable t) {

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
