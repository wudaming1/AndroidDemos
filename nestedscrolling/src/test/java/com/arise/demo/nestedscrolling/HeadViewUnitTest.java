package com.arise.demo.nestedscrolling;

import com.aries.base.BaseApplication;
import com.aries.base.utils.DensityUtils;
import com.arise.demo.nestedscrolling.activities.StretchHeadActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by wudaming on 2018/4/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = BaseApplication.class)
public class HeadViewUnitTest {

    private StretchHeadActivity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(StretchHeadActivity.class);
    }

    @Test
    public void changeHeightTest() {
        MutableRelativeLayout headView = activity.findViewById(R.id.head);
        int inputOffset = DensityUtils.INSTANCE.dip2px(70);
        int height = headView.getHeight();
        int currentHeight = height - (inputOffset - headView.changeHeight(inputOffset));
        headView.requestLayout();
        Assert.assertEquals(headView.getHeight(), currentHeight);

    }
}
