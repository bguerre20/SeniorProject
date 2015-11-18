package com.ben.bryan.wilburn.roomies;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;
import android.widget.Button;
import android.content.Intent;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityUnitTestCase<MyActivity> {
    public ApplicationTest() {
        super(MyActivity.class);
    }

    private MyActivity mActivity; // MyActivity is the class name of the app under test
    private Button mEms;
    private Intent mActivityIntent;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity(); // get a references to the app under test
        mActivityIntent = new Intent(getInstrumentation().getTargetContext(),
                MyActivity.class);


      /*
       * Get a reference to the main widget of the app under test, a Spinner
       */
        //mEms = (Button) mActivity.findViewById(R.id.emsButton);

    }
    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mActivityIntent, null, null);
        final Button launchNextButton = (Button) mActivity.findViewById(R.id.emsButton);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());


    }
}