package com.arise.android.demo.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by wudaming on 2018/3/28.
 */
class MyTextView : TextView {

    private val tag = MyTextView::class.java.simpleName



    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(tag, "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e(tag,"onTouchEvent")
//        return super.onTouchEvent(event)
        return true
    }


}