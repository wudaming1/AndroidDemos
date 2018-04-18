package com.arise.demo.nestedscrolling;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.arise.demo.nestedscrolling.activities.StretchHeadActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by wudaming on 2018/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class HeadViewTest {

    @Rule
    public ActivityTestRule<StretchHeadActivity> mActivityRule
            = new ActivityTestRule<>(StretchHeadActivity.class);

    @Test
    public void testChangeHeight() {
        Espresso.onView(ViewMatchers.withId(R.id.head))
                .perform(ViewActions.click());

    }
}
