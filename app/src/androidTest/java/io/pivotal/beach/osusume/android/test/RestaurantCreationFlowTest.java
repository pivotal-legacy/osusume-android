package io.pivotal.beach.osusume.android.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.pivotal.beach.osusume.android.test.matchers.RecyclerViewMatcher.withRecyclerView;
import static io.pivotal.beach.osusume.android.test.matchers.UserActions.clickOn;
import static io.pivotal.beach.osusume.android.test.matchers.UserActions.fillIn;
import static io.pivotal.beach.osusume.android.test.matchers.UserActions.selectInSpinner;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class RestaurantCreationFlowTest {
    Calendar today = Calendar.getInstance();

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void user_creates_restaurant() throws Exception {
        // Login
        fillIn(R.id.loginEmail, "danny");
        fillIn(R.id.loginPassword, "danny");
        onView(withId(R.id.loginPassword)).perform(closeSoftKeyboard());
        clickOn(R.id.loginButton);

        sleep(2000);

        clickOn(R.id.addRestaurantButton);
        fillIn(R.id.newRestaurantNameField, "Barbacoa");
        selectInSpinner(R.id.newRestaurantCuisineTypeSpinner, "Japanese");
        selectInSpinner(R.id.newRestaurantPriceRangeSpinner, "¥0~999");
        clickOn(R.id.createRestaurant);

        sleep(2000);

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantName))
                .check(matches(withText("Barbacoa")));
        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantAuthor))
                .check(matches(withText("Added by Danny")));
    }
}
