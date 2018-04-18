package com.arise.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

/**
 * 嵌套滑动机制接口实现版本
 */
class NestedScrollingParent1 : LinearLayout, NestedScrollingParent {

    private val TAG = NestedScrollingParent1::class.java.simpleName

    private val mParentHelper = NestedScrollingParentHelper(this)

    private lateinit var head: MutableRelativeLayout

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.nested_scrolling_parent_layout, this, true)
        head = findViewById(R.id.head)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll:${axes == ViewCompat.SCROLL_AXIS_VERTICAL}")
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }



    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedPreScroll")
//        val re = canScrollVertical(dy)
//        if (re) {
//            consumed[0] = 0
//            consumed[1] = doScrollVertical(dy)
//        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        mParentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(target: View) {
        mParentHelper.onStopNestedScroll(target)
    }

    override fun getNestedScrollAxes(): Int {
        return mParentHelper.nestedScrollAxes
    }

//
//    private fun canScrollVertical(offset: Int): Boolean {
//        return if (offset < 0) {
//            head.canShrink()
//        } else {
//            head.canStretch()
//        }
//    }

    /**
     * @return 真实消耗的滑动距离
     * */
    private fun doScrollVertical(totalOffset: Int): Int {
        Log.e(TAG,"doScrollVertical:$totalOffset")
        val remain = head.changeHeight(totalOffset)
        return totalOffset - remain
    }

}