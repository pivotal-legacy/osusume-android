package io.pivotal.beach.osusume.android.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.format.DateFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.pivotal.beach.osusume.android.test.matchers.RecyclerViewMatcher.withRecyclerView;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantCreationFlowTest {
    Calendar today = Calendar.getInstance();
    Date date = new Date();

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void user_creates_restaurant() throws Exception {
        // Login
        onView(withId(R.id.loginEmail)).perform(typeText("A"));
        onView(withId(R.id.loginPassword)).perform(typeText("A"));
        onView(withId(R.id.loginButton)).perform(click());
        sleep(2000); // TODO: DO NOT SLEEP

        // Create Restaurant
        String newRestaurantName = "New Restaurant " + date.toString();

        onView(withId(R.id.addRestaurantButton)).perform(click());
        onView(withId(R.id.newRestaurantNameField)).perform(typeText(newRestaurantName));
        onView(withId(R.id.createRestaurant)).perform(click());
        sleep(2000);

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantAuthor))
                .check(matches(withText("Added by A")));

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantCreatedAt))
                .check(matches(withText("Created on " + DateFormat.format("MM/dd/yy", today).toString())));

        // Click on First Restaurant
        onView(withId(R.id.restaurantListView)).perform(actionOnItemAtPosition(0, click()));
        sleep(2000);

        onView(withId(R.id.restaurantDetailsName))
                .check(matches(withText(containsString(newRestaurantName))));
    }
}
