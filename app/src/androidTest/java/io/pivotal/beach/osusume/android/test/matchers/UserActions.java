package io.pivotal.beach.osusume.android.test.matchers;

import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class UserActions {

    public static void assertDialogDisplaysText(String text) {
        onView(withText(text)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    public static void assertToastDisplaysText(String text, ActivityTestRule rule) {
        onView(withText(text))
                .inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    public static void assertDisplaysText(String expected) {
        onView(withText(containsString(expected))).check(matches(isDisplayed()));
    }

    public static void fillInDialog(int id, String espresso) {
        onView(withId(id)).perform(typeTextIntoFocusedView(espresso), closeSoftKeyboard());
    }

    public static void fillIn(int id, String espresso) {
        onView(withId(id)).perform(typeText(espresso), closeSoftKeyboard());
    }

    public static void clickOn(int id) {
        onView(withId(id)).perform(click());
    }

    public static void clickOn(String text) {
        onView(withText(text)).perform(click());
    }

    public static void clickButtonInDialog(String text) {
        onView(allOf(withClassName(endsWith("Button")), withText(text))).inRoot(isDialog()).perform(click());
    }

    public static void clickButtonInDialog(int id) {
        onView(allOf(withClassName(endsWith("Button")), withId(id))).inRoot(isDialog()).perform(click());
    }

    public static void clickButton(String text) {
        onView(allOf(withClassName(endsWith("Button")), withText(text))).perform(click());
    }

    public static void selectInSpinner(int resId, String text) {
        onView(withId(resId)).perform(click());
        clickOn(text);
    }
}