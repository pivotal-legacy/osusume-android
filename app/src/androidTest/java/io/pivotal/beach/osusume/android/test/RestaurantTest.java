package io.pivotal.beach.osusume.android.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.format.DateFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.pivotal.beach.osusume.android.activities.OsusumeActivity;
import io.pivotal.beach.osusume.android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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

    @Rule
    public ActivityTestRule<OsusumeActivity> mActivityRule = new ActivityTestRule<>(OsusumeActivity.class);

    @Test
    public void changeText_sameActivity() throws Exception {
        // TODO: DO NOT SLEEP
        sleep(2000);

//        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantName))
//                .check(matches(withText(containsString("2016-"))));

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantAuthor))
                .check(matches(withText("Added by A")));

        onView(withRecyclerView(R.id.restaurantListView).atPositionOnView(0, R.id.restaurantCreatedAt))
                .check(matches(withText(dateAsString())));

        onView(withId(R.id.restaurantListView))
                .perform(actionOnItemAtPosition(0, click()));

        // TODO: DO NOT SLEEP
        sleep(2000);

        onView(withId(R.id.restaurantDetailsName))
                .check(matches(withText(containsString("2016-"))));
    }

    private String dateAsString() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        return "Created on " + DateFormat.format("MM/dd/yy", today).toString();
    }
}
