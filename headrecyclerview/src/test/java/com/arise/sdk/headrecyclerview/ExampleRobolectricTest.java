package com.arise.sdk.headrecyclerview;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

/**
 * Created by wudaming on 2018/4/2.
 */

@RunWith(RobolectricTestRunner.class)
public class ExampleRobolectricTest {

    @Test
    public void clickingLogin_shouldStartMineActivity() throws Exception {

        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.findViewById(R.id.login).performClick();

        Intent expectedIntent = new Intent(activity,MineActivity.class);

        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}
