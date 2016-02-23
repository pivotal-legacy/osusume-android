package io.pivotal.beach.osusume.android.test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import io.pivotal.beach.osusume.android.OsusumeActivity;


public class RestaurantTest extends ActivityInstrumentationTestCase2<OsusumeActivity> {
  	private Solo solo;

  	public RestaurantTest() {
		super(OsusumeActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}

   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }


	public void test_I_see_restaurants() {
		solo.waitForActivity(OsusumeActivity.class, 2000);

        assertTrue(solo.searchText("Something Else"));
        assertTrue(solo.searchText("Added by A"));
        assertTrue(solo.searchText("Created on 02/18/16"));
	}
}
