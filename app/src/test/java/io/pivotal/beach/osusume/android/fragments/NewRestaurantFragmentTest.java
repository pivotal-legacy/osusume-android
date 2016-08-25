package io.pivotal.beach.osusume.android.fragments;

import android.widget.Spinner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import io.pivotal.beach.osusume.android.AppComponent;
import io.pivotal.beach.osusume.android.BuildConfig;
import io.pivotal.beach.osusume.android.DaggerTestComponent;
import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.TestModule;
import io.pivotal.beach.osusume.android.models.NewRestaurant;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Robolectric.flushBackgroundThreadScheduler;
import static org.robolectric.Robolectric.flushForegroundThreadScheduler;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class NewRestaurantFragmentTest {

    private NewRestaurantFragment fragment;

    private MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws Exception {
        fragment = new NewRestaurantFragment();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testOnCreateView_callsCuisineAndPriceRangeApis() throws Exception {
        injectTestModule();
        setupMockResponses();
        startFragment(fragment);

        RecordedRequest request1 = server.takeRequest(5, SECONDS);
        RecordedRequest request2 = server.takeRequest(5, SECONDS);
        assertNotNull(request1);
        assertNotNull(request2);

        Set<String> requests = new HashSet<>(asList(request1.getPath(), request2.getPath()));
        assertThat(requests).contains("/cuisines", "/priceranges");
    }

    @Test
    public void testOnCreateView_populatesCuisines() throws Exception {
        injectTestModule();
        setupMockResponses();
        startFragment(fragment);
        tick();

        Spinner spinner = fragment.cuisineSpinner;
        assertThat(spinner.getItemAtPosition(0)).isEqualTo("Not Specified");
        assertThat(spinner.getItemAtPosition(1)).isEqualTo("Japanese");
    }

    @Test
    public void testOnCreateView_populatesPriceRanges() throws Exception {
        injectTestModule();
        setupMockResponses();
        startFragment(fragment);
        tick();

        Spinner spinner = fragment.priceRangeSpinner;
        assertThat(spinner.getItemAtPosition(0)).isEqualTo("Not Specified");
        assertThat(spinner.getItemAtPosition(1)).isEqualTo("¥0~999");
    }

    @Test
    public void test_getNewRestaurant() throws Exception {
        injectTestModule();
        setupMockResponses();
        startFragment(fragment);
        tick();
        fragment.restaurantNameField.setText("Afuri");

        NewRestaurant actual = fragment.getNewRestaurant();

        assertThat(actual.getName()).isEqualTo("Afuri");
        assertThat(actual.getCuisineTypeId()).isEqualTo(0);
        assertThat(actual.getPriceRangeId()).isEqualTo(0);
    }

    private void setupMockResponses() {
        server.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/cuisines":
                        return new MockResponse().setResponseCode(200).setBody("[{\"id\":0,\"name\":\"Not Specified\"},{\"id\":1,\"name\":\"Japanese\"}]");
                    case "/priceranges":
                        return new MockResponse().setResponseCode(200).setBody("[{\"id\":0,\"range\":\"Not Specified\"},{\"id\":1,\"range\":\"¥0~999\"}]");
                }
                return new MockResponse().setResponseCode(404);
            }
        });
    }

    private void tick() throws Exception {
        flushBackgroundThreadScheduler();
        new CountDownLatch(1).await(100, MILLISECONDS);
        flushForegroundThreadScheduler();
    }

    private void injectTestModule() {
        AppComponent appComponent = DaggerTestComponent.builder()
                .testModule(new TestModule(server.url("")))
                .build();

        ((OsusumeApplication) RuntimeEnvironment.application).setAppComponent(appComponent);
    }
}
