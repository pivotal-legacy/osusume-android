package io.pivotal.beach.osusume.android.test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import io.pivotal.beach.osusume.android.OsusumeActivity;


public class MyTest extends ActivityInstrumentationTestCase2<OsusumeActivity> {
  	private Solo solo;
  	
  	public MyTest() {
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
  
	public void testRun() {
        //Wait for activity: 'io.pivotal.beach.osusume.android.OsusumeActivity'
		solo.waitForActivity(io.pivotal.beach.osusume.android.OsusumeActivity.class, 2000);

        //Click on ImageView
		solo.clickOnView(solo.getView(io.pivotal.beach.osusume.android.R.id.fab));
        //Click on ImageView
		solo.clickOnView(solo.getView(io.pivotal.beach.osusume.android.R.id.fab));
	}
}
