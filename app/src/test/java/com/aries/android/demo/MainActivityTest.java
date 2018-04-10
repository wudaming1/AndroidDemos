package com.aries.android.demo;

import android.content.Intent;

import com.aries.sdk.recyclerview.activitys.HomeActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

/**
 * Created by wudaming on 2018/4/9.
 */

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void goRecyclerViewDemo(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Intent expectedIntent = new Intent(activity, HomeActivity.class);

        activity.findViewById(R.id.goRecyclerDemo).performClick();

        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
