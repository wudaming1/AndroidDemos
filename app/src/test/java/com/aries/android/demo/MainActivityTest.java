package com.aries.android.demo;

import com.aries.android.demo.touch.TouchActivity;
import com.aries.demo.materialdesign.MaterialActivity;
import com.aries.demo.testbase.ActivityTest;
import com.aries.sdk.recyclerview.RecyclerViewActivity;
import com.arise.demo.nestedscrolling.activities.NestedScrollingMainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by wudaming on 2018/4/9.
 */

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest extends ActivityTest {


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
}
