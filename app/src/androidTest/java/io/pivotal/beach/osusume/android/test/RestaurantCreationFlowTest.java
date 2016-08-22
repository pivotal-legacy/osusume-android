package io.pivotal.beach.osusume.android.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RestaurantCreationFlowTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void user_creates_restaurant() throws Exception {
        // Login
        onView(withId(R.id.loginEmail)).perform(typeText("danny"));
        onView(withId(R.id.loginPassword)).perform(typeText("danny"));
        onView(withId(R.id.loginPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }
}
