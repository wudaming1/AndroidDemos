package com.aries.sdk.recyclerview.activitys;

import com.aries.demo.testbase.ActivityTest;
import com.aries.sdk.recyclerview.R;
import com.aries.sdk.recyclerview.RecyclerViewActivity;
import com.aries.sdk.recyclerview.grid.GridActivity;
import com.aries.sdk.recyclerview.learning.LearningActivity;
import com.aries.sdk.recyclerview.sticky.StickyActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by wudaming on 2018/4/9.
 */

@RunWith(RobolectricTestRunner.class)
public class RecyclerViewActivityTest extends ActivityTest {

    @Before
    public void init() {
        activity = Robolectric.setupActivity(RecyclerViewActivity.class);
    }

    @Test
    public void testGoStick() {
        checkGoActivity(R.id.goSticky, StickyActivity.class);
    }

    @Test
    public void testGoGrid() {
        checkGoActivity(R.id.goGrid, GridActivity.class);
    }

    @Test
    public void testGoLearning() {
        checkGoActivity(R.id.goLearning, LearningActivity.class);
    }

}
