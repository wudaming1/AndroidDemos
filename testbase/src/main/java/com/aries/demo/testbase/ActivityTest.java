package com.aries.demo.testbase;

import android.app.Activity;
import android.content.Intent;

import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertEquals;

/**
 * Author wudaming
 * Created on 2018/4/26
 */
public class ActivityTest {

    protected Activity activity;

    protected void checkGoActivity(int id, Class<? extends Activity> targetActivity) {
        activity.findViewById(id).performClick();
        assertResult(targetActivity);
    }

    private void assertResult(Class clazz) {
        Intent expectedIntent = new Intent(activity, clazz);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
