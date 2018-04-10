package com.aries.android.demo.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * Created by wudaming on 2018/3/28.
 */
class MyRelativeLayout:RelativeLayout{
    private val tag = MyRelativeLayout::class.java.simpleName

    private val consumeTimes = 10

    private var currentConsume = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(tag,"dispatchTouchEvent")
        currentConsume++
        if (ev!!.action == MotionEvent.ACTION_UP){
            currentConsume = 0
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(tag,"onInterceptTouchEvent")
        if (canConsume()){
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e(tag, "onTouchEvent:$currentConsume")
        return super.onTouchEvent(event)
    }

    private fun canConsume(): Boolean {
        return currentConsume >= consumeTimes
    }
}