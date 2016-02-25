package io.pivotal.beach.osusume.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.api.AuthorizationHeaderInterceptor;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.fragments.NewRestaurantFragment;
import io.pivotal.beach.osusume.android.fragments.RestaurantListFragment;
import io.pivotal.beach.osusume.android.models.Login;
import io.pivotal.beach.osusume.android.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListActivity extends AppCompatActivity {

    @Inject
    AuthorizationHeaderInterceptor interceptor;

    @Inject
    OsusumeApiClient osusumeApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((OsusumeApplication) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.restaurantListFragment, new RestaurantListFragment(), NewRestaurantFragment.TAG)
                .commit();

        login();
    }

    void login() {
        osusumeApiClient.login(new Login("A", "A")).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                interceptor.setToken(response.body().getToken());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
            }
        });
    }
}
