package io.pivotal.beach.osusume.android;

import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.CountDownLatch;

import okhttp3.HttpUrl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.robolectric.Robolectric.flushBackgroundThreadScheduler;
import static org.robolectric.Robolectric.flushForegroundThreadScheduler;

public class TestHelper {
    public static void injectTestModule(HttpUrl baseUrl) {
        AppComponent appComponent = DaggerTestComponent.builder()
                .testModule(new TestModule(baseUrl))
                .build();

        ((OsusumeApplication) RuntimeEnvironment.application).setAppComponent(appComponent);
    }

    public static void flushThreadSchedulers() throws Exception {
        flushBackgroundThreadScheduler();
        new CountDownLatch(1).await(100, MILLISECONDS);
        flushForegroundThreadScheduler();
    }
}
