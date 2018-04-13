package com.aries.android.demo;

import android.content.Intent;

import com.aries.android.demo.touch.TouchActivity;
import com.aries.sdk.recyclerview.activitys.RecyclerHomeActivity;
import com.arise.demo.nestedscrolling.activities.NestedScrollingMainActivity;

import org.junit.Before;
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

    private MainActivity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void goTouchExp() {
        Intent expectedIntent = new Intent(activity, TouchActivity.class);

        activity.findViewById(R.id.goTouch).performClick();

        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void goNestedScrollingDemo() {
        Intent expectedIntent = new Intent(activity, NestedScrollingMainActivity.class);

        activity.findViewById(R.id.goNestedScrolling).performClick();

        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void goRecyclerViewDemo() {
        Intent expectedIntent = new Intent(activity, RecyclerHomeActivity.class);

        activity.findViewById(R.id.goRecyclerDemo).performClick();

        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
