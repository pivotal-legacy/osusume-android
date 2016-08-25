package io.pivotal.beach.osusume.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.api.AuthorizationHeaderInterceptor;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.models.Login;
import io.pivotal.beach.osusume.android.models.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    public static final String OSUSUME_TOKEN_STORE = "OSUSUME_TOKEN_STORE";

    public static final String OSUSUME_TOKEN = "OSUSUME_TOKEN";

    @Inject
    AuthorizationHeaderInterceptor interceptor;

    @Inject
    OsusumeApiClient osusumeApiClient;

    @Bind(R.id.loginEmail)
    EditText emailField;

    @Bind(R.id.loginPassword)
    EditText passwordField;

    private SharedPreferences tokenStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((OsusumeApplication) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        tokenStore = getSharedPreferences(OSUSUME_TOKEN_STORE, Context.MODE_PRIVATE);
        String token = tokenStore.getString(OSUSUME_TOKEN, null);
        if (token != null) {
            interceptor.setToken(token);
            startRestaurantListActivity();
            finish();
        }
    }

    @OnClick(R.id.loginButton)
    void onLoginButtonSelected(View v) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        login(email, password);
    }

    private void login(String email, String password) {
        Login login = new Login(email, password);
        osusumeApiClient.login(login).enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                String token = response.body().getToken();
                interceptor.setToken(token);
                tokenStore.edit()
                    .putString(OSUSUME_TOKEN, token)
                    .apply();

                startRestaurantListActivity();
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) { }
        });
    }

    private void startRestaurantListActivity() {
        Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
        startActivity(intent);
    }
}
