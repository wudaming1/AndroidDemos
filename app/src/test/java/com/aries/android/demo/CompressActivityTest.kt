package com.aries.android.demo

import android.os.Handler
import android.os.Looper
import android.widget.EditText
import com.aries.android.demo.compress.CompressActivity
import com.aries.android.demo.compress.CompressViewModel
import com.aries.demo.testbase.ActivityTest
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Author wudaming
 * Created on 2018/6/20
 */

@RunWith(RobolectricTestRunner::class)
class CompressActivityTest : ActivityTest() {

    private lateinit var viewModel: CompressViewModel

    @Before
    fun init() {
        Robolectric.setupActivity(CompressActivity::class.java)!!.apply {
            activity = this
            viewModel = compressViewModel
        }
    }


    @Test
    fun testInPut() {

        val width = "800"
        val height = "480"

        val widthEdit = activity.findViewById<EditText>(R.id.width)
        val heightEdit = activity.findViewById<EditText>(R.id.height)

        widthEdit.setText(width)
        heightEdit.setText(height)

        Handler(Looper.getMainLooper()).postDelayed({
            assertEquals("宽度设置错误", width, viewModel.width.get())
            assertEquals("高度设置错误", height, viewModel.height.get())
        }, 100)


    }

}