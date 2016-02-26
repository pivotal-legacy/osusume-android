package io.pivotal.beach.osusume.android.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.pivotal.beach.osusume.android.BuildConfig;
import io.pivotal.beach.osusume.android.models.Restaurant;
import io.pivotal.beach.osusume.android.models.User;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class RestaurantPresenterTest {

    private RestaurantPresenter presenter;

    @Before
    public void setup() {
        long timestamp = 1456447726; // represents 2016-02-26 09:48:46 +0900
        User user = new User("Danny Burkes");
        Restaurant restaurant = new Restaurant(1, "Butagumi", timestamp, user);

        presenter = new RestaurantPresenter(restaurant);
    }

    @Test
    public void test_getCreatedAt_formats_timestamp() throws Exception {
        assertEquals("Created on 02/26/16", presenter.getCreatedAtInText());
    }

    @Test
    public void test_getAuthorName_formats_userName() throws Exception {
        assertEquals("Added by Danny Burkes", presenter.getAuthorName());
    }
}