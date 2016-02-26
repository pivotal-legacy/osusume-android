package io.pivotal.beach.osusume.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.fragments.NewRestaurantFragment;
import io.pivotal.beach.osusume.android.fragments.RestaurantListFragment;

public class RestaurantListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.restaurantListFragment, new RestaurantListFragment(), NewRestaurantFragment.TAG)
                .commit();

    }
}
