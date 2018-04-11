package com.arise.demo.nestedscrolling;

import android.app.Activity;
import android.content.Intent;

import com.arise.demo.nestedscrolling.activities.BuildInNestedActivity;
import com.arise.demo.nestedscrolling.activities.HeadRecyclerViewActivity;
import com.arise.demo.nestedscrolling.activities.InterfaceNestedActivity;
import com.arise.demo.nestedscrolling.activities.ZoomHeadActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

/**
 * 主要是页面跳转测试
 */

@RunWith(RobolectricTestRunner.class)
public class NestedScrollingMainActivityTest {

    private NestedScrollingMainActivity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(NestedScrollingMainActivity.class);
    }

    @Test
    public void goZoomTest() {
        checkGoActivity(R.id.goZoom, ZoomHeadActivity.class);
    }

    @Test
    public void goHeadRecyclerViewActivity() {
        checkGoActivity(R.id.goHead, HeadRecyclerViewActivity.class);
    }

    @Test
    public void goBuildInNested() {
        checkGoActivity(R.id.goBuildIn, BuildInNestedActivity.class);
    }

    @Test
    public void goInterfaceNested() {
        checkGoActivity(R.id.goInterfaceNested, InterfaceNestedActivity.class);
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
