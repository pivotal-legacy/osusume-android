package io.pivotal.beach.osusume.android.activities;

import android.app.Activity;
import android.content.Intent;
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
import io.pivotal.beach.osusume.android.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    @Inject
    AuthorizationHeaderInterceptor interceptor;

    @Inject
    OsusumeApiClient osusumeApiClient;

    @Bind(R.id.loginEmail)
    EditText emailField;

    @Bind(R.id.loginPassword)
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((OsusumeApplication) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonSelected(View v) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        login(email, password);
    }

    void login(String email, String password) {
        Login login = new Login(email, password);
        osusumeApiClient.login(login).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                interceptor.setToken(response.body().getToken());
                Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
            }
        });
    }
}
