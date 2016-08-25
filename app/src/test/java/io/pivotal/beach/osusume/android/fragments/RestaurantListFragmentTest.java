package io.pivotal.beach.osusume.android.fragments;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.pivotal.beach.osusume.android.BuildConfig;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.activities.NewRestaurantActivity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RestaurantListFragmentTest {

    private RestaurantListFragment fragment;

    @Before
    public void setUp() throws Exception {
        fragment = new RestaurantListFragment();
    }

    @Test
    public void testOnAddRestaurantButtonClicked() throws Exception {
        startFragment(fragment);

        fragment.getView().findViewById(R.id.addRestaurantButton).performClick();

        Intent nextStartedActivity = shadowOf(fragment.getActivity()).getNextStartedActivity();
        assertThat(nextStartedActivity.getComponent().getClassName())
                .isEqualTo(NewRestaurantActivity.class.getCanonicalName());
    }
}