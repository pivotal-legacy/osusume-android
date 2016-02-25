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
import io.pivotal.beach.osusume.android.activities.RestaurantListActivity;

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
public class RestaurantTest {
    Calendar today = Calendar.getInstance();
    Date date = new Date();

    @Rule
    public ActivityTestRule<RestaurantListActivity> mActivityRule = new ActivityTestRule<>(RestaurantListActivity.class);

    @Test
    public void changeText_sameActivity() throws Exception {
        // TODO: DO NOT SLEEP
        sleep(2000);

//        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantName))
//                .check(matches(withText(containsString("2016-"))));

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantAuthor))
                .check(matches(withText("Added by A")));

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantCreatedAt))
                .check(matches(withText("Created on " + DateFormat.format("MM/dd/yy", today).toString())));

        String newRestaurantName = "New Restaurant " + date.toString();

        // Create Restaurant
        onView(withId(R.id.addRestaurantButton))
                .perform(click());

        onView(withId(R.id.newRestaurantNameField))
                .perform(typeText(newRestaurantName));

        onView(withId(R.id.createRestaurant))
                .perform(click());

        // TODO: DO NOT SLEEP
        sleep(2000);

        // Click on First Restaurant
        onView(withId(R.id.restaurantListView))
                .perform(actionOnItemAtPosition(0, click()));

        // TODO: DO NOT SLEEP
        sleep(2000);

        onView(withId(R.id.restaurantDetailsName))
                .check(matches(withText(containsString(newRestaurantName))));
    }
}
