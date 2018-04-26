package com.aries.android.demo;

import android.app.Activity;
import android.content.Intent;

import com.aries.android.demo.touch.TouchActivity;
import com.aries.demo.materialdesign.MaterialActivity;
import com.aries.sdk.recyclerview.RecyclerViewActivity;
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
    public void goTouch() {
        checkGoActivity(R.id.goTouch, TouchActivity.class);
    }

    @Test
    public void goNestedScrollingDemo() {
        checkGoActivity(R.id.goNestedScrolling, NestedScrollingMainActivity.class);
    }

    @Test
    public void goRecyclerViewDemo() {
        checkGoActivity(R.id.goRecyclerDemo, RecyclerViewActivity.class);
    }

    @Test
    public void goMaterialDemo() {
        checkGoActivity(R.id.goMaterialDemo, MaterialActivity.class);
    }

    private void checkGoActivity(int id, Class<? extends Activity> targetActivity) {
        activity.findViewById(id).performClick();
        assertResult(targetActivity);
    }

    private void assertResult(Class clazz) {
        Intent expectedIntent = new Intent(activity, clazz);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
