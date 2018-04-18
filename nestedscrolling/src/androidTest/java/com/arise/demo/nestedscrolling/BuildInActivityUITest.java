package com.arise.demo.nestedscrolling;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.MotionEvents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MotionEvent;
import android.view.View;

import com.arise.demo.nestedscrolling.activities.StretchHeadActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by wudaming on 2018/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class BuildInActivityUITest {

    @Rule
    public ActivityTestRule<StretchHeadActivity> mActivityRule
            = new ActivityTestRule<>(StretchHeadActivity.class);

    public static ViewAction touchEvent(final float[] start, final float[] end) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Send touch events.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                // Get view absolute position
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                //下滑正数
                boolean direction = end[1] > start[1];

                // Offset coordinates by view position
                float[] coordinates = new float[]{start[0] + location[0], start[1] + location[1]};
                float[] endCoordinates = new float[]{end[0] + location[0], end[1] + location[1]};
                float[] precision = new float[]{1f, 1f};

                // Send down , move, and  up
                MotionEvent down = MotionEvents.sendDown(uiController, coordinates, precision).down;

                if (direction) {
                    coordinates[1] += 5;
                } else {
                    coordinates[1] -= 5;
                }

                while (coordinates[1] < endCoordinates[1]) {
                    uiController.loopMainThreadForAtLeast(20);
                    MotionEvents.sendMovement(uiController, down, coordinates);
                    if (direction) {
                        coordinates[1] += 5;
                    } else {
                        coordinates[1] -= 5;
                    }
                }
                uiController.loopMainThreadForAtLeast(20);
                MotionEvents.sendUp(uiController, down, endCoordinates);
            }
        };
    }

    @Test
    public void recyclerView_TouchTest() {
        float[] start = new float[]{100, 100};
        float[] end = new float[]{100, 300};
        Espresso.onView(ViewMatchers.withId(R.id.nestedScrollingChild))
                .perform(touchEvent(start, end));
    }
}


