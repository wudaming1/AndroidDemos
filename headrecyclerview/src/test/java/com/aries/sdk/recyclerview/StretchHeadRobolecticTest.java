package com.aries.sdk.recyclerview;

import android.content.Context;
import android.support.v4.view.ViewCompat;

import com.aries.sdk.recyclerview.head.StretchHead;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;


/**
 * Created by wudaming on 2018/4/3.
 */

@RunWith(RobolectricTestRunner.class)
public class StretchHeadRobolecticTest {

    @Test
    public void checkAcceptScrollVertical() throws Exception {
        Context context = ShadowApplication.getInstance().getApplicationContext();

        StretchHead stretchHead = new StretchHead(context);

        Assert.assertTrue(stretchHead.acceptScroll(ViewCompat.SCROLL_AXIS_VERTICAL));
        Assert.assertTrue(stretchHead.acceptScroll((ViewCompat.SCROLL_AXIS_VERTICAL | ViewCompat.SCROLL_AXIS_HORIZONTAL)));
        Assert.assertFalse(stretchHead.acceptScroll(ViewCompat.SCROLL_AXIS_NONE));
        Assert.assertFalse(stretchHead.acceptScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL));
    }

}
