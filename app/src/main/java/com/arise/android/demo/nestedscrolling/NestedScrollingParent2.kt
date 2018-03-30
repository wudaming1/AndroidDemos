package com.arise.android.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.arise.android.demo.R

/**
 * Created by wudaming on 2018/3/30.
 */
class NestedScrollingParent2 : LinearLayout {

    private val TAG = NestedScrollingParent2::class.java.simpleName

    private lateinit var head: HeadView

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

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll:${nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL}")
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedPreScroll")
        val re = canScrollVertical(dy)
        if (re) {
            consumed[0] = 0
            consumed[1] = doScrollVertical(dy)
        }
    }

    private fun canScrollVertical(offset: Int): Boolean {
        return if (offset < 0) {
            head.canShrink()
        } else {
            head.canStretch()
        }
    }

    /**
     * @return 真实消耗的滑动距离
     * */
    private fun doScrollVertical(totalOffset: Int): Int {
        Log.e(TAG, "doScrollVertical:$totalOffset")
        val remain = head.changeHeight(totalOffset)
        return totalOffset - remain
    }

}