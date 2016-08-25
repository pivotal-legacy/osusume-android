package io.pivotal.beach.osusume.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.pivotal.beach.osusume.android.BuildConfig;
import io.pivotal.beach.osusume.android.R;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.content.Context.MODE_PRIVATE;
import static io.pivotal.beach.osusume.android.TestHelper.flushThreadSchedulers;
import static io.pivotal.beach.osusume.android.TestHelper.injectTestModule;
import static io.pivotal.beach.osusume.android.activities.LoginActivity.OSUSUME_TOKEN;
import static io.pivotal.beach.osusume.android.activities.LoginActivity.OSUSUME_TOKEN_STORE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Robolectric.setupActivity;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.ShadowView.clickOn;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginActivityTest {

    private MockWebServer server = new MockWebServer();

    @Test
    public void test_onLoginButtonSelected_callsLoginApi() throws Exception {
        injectTestModule(server.url(""));
        setupMockResponses();

        LoginActivity activity = setupActivity(LoginActivity.class);
        activity.emailField.setText("danny");
        activity.passwordField.setText("danny");

        clickOn(activity.findViewById(R.id.loginButton));
        flushThreadSchedulers();

        RecordedRequest request = server.takeRequest(5, SECONDS);

        assertNotNull(request);
        assertThat(request.getPath()).isEqualTo("/session");

        String token = activity.interceptor.getToken();
        assertThat(token).isEqualTo("507ac7hqnaenfcupvg1qbljajg");

        Intent nextStartedActivity = shadowOf(activity).getNextStartedActivity();
        assertThat(nextStartedActivity.getComponent().getClassName())
                .isEqualTo(RestaurantListActivity.class.getCanonicalName());

        SharedPreferences sharedPreferences = RuntimeEnvironment.application
                .getSharedPreferences(OSUSUME_TOKEN_STORE, MODE_PRIVATE);

        assertThat(sharedPreferences.getString(OSUSUME_TOKEN, "")).isEqualTo("507ac7hqnaenfcupvg1qbljajg");
    }

    @Test
    public void test_LoginActivity_startsRestaurantListAcitivty_whenTokenIsPersisted() throws Exception {
        RuntimeEnvironment.application
                .getSharedPreferences(OSUSUME_TOKEN_STORE, MODE_PRIVATE)
                .edit()
                .putString(OSUSUME_TOKEN, "token")
                .apply();

        LoginActivity activity = setupActivity(LoginActivity.class);

        String token = activity.interceptor.getToken();
        assertThat(token).isEqualTo("token");

        Intent nextStartedActivity = shadowOf(activity).getNextStartedActivity();
        assertThat(nextStartedActivity.getComponent().getClassName())
                .isEqualTo(RestaurantListActivity.class.getCanonicalName());

        assertTrue("Activity is not finishing", shadowOf(activity).isFinishing());
    }

    private void setupMockResponses() {
        server.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/session":
                        return new MockResponse().setResponseCode(200)
                                .setBody("{\"name\":\"Danny\",\"id\":19,\"email\":\"danny\",\"token\":\"507ac7hqnaenfcupvg1qbljajg\"}");
                }
                return new MockResponse().setResponseCode(404);
            }
        });
    }

}
