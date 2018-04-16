package com.arise.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

/**
 * 嵌套滑动机制内置实现版本
 */
class NestedScrollingParent2View : LinearLayout, NestedScrollingParent2 {

    private val INVALID_POINTER = -1

    private var mLastTouchX = 0
    private var mLastTouchY = 0

    private var mScrollPointerId = INVALID_POINTER


    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        scrollingView = target
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    private val TAG = NestedScrollingParent2View::class.java.simpleName

    private val linkedChildren = ArrayList<LinkedChild>()

    var scrollingView: View? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        findLinkScrollChildren()

    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.actionMasked
        val actionIndex = e.actionIndex
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                mLastTouchX = (e.x + 0.5f).toInt()
                mLastTouchY = (e.y + 0.5f).toInt()
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = e.getPointerId(actionIndex)
                mLastTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                mLastTouchY = (e.getY(actionIndex) + 0.5f).toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id "
                            + mScrollPointerId + " not found. Did any MotionEvents get skipped?")
                    return false
                }

                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                var dx = mLastTouchX - x
                var dy = mLastTouchY - y
                val mScrollConsumed = IntArray(2)
                dispatchInternal(dx, dy, mScrollConsumed)
                dx -= mScrollConsumed[0]
                dy -= mScrollConsumed[1]
                if (dx != 0 || dy != 0) {
                    scrollingView?.apply { this.scrollBy(dx, dy) }
                }
                mLastTouchX = x
                mLastTouchY = y
            }

            MotionEvent.ACTION_POINTER_UP -> onPointerUp(e)

            MotionEvent.ACTION_UP -> {
                dispatchStop()
            }
            MotionEvent.ACTION_CANCEL -> dispatchStop()

        }

        return true
    }

    private fun onPointerUp(e: MotionEvent) {
        val actionIndex = e.actionIndex
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            val newIndex = if (actionIndex == 0) 1 else 0
            mScrollPointerId = e.getPointerId(newIndex)
            mLastTouchX = (e.getX(newIndex) + 0.5f).toInt()
            mLastTouchY = (e.getY(newIndex) + 0.5f).toInt()
        }
    }

    private fun dispatchStop() {
        if (linkedChildren.isNotEmpty()) {
            linkedChildren.forEach {
                it.onStopLinkedScroll()
            }
        }
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray?, type: Int) {

        if (target.canScrollVertically(-1) && dy < 0) {
            return
        }
        val safeConsumed = IntArray(2)
        dispatchInternal(dx, dy, safeConsumed)
        if (consumed != null) {
            consumed[0] = safeConsumed[0]
            consumed[1] = safeConsumed[1]
        }

    }


    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        if (child is LinkedChild) {
            refreshLinkedChildren()
        }
    }

    private fun refreshLinkedChildren() {
        linkedChildren.clear()
        findLinkScrollChildren()
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        if (child is LinkedChild)
            deleteLinkedChild(child)
    }

    private fun deleteLinkedChild(child: LinkedChild) {
        if (linkedChildren.contains(child) && child.enableLinkedScroll) {
            linkedChildren.remove(child)
        }
    }

    private fun dispatchInternal(dx: Int, dy: Int, consumed: IntArray) {
        val childrenConsume = intArrayOf(0, 0)
        dispatchToChildren(dx, dy, childrenConsume)
        consumed[0] += childrenConsume[0]
        consumed[1] += childrenConsume[1]
    }

    private fun dispatchToChildren(dx: Int, dy: Int, consumed: IntArray) {
        var unconsumedX = dx
        var unconsumedY = dy
        val childConsumed = IntArray(2)
        if (linkedChildren.isNotEmpty()) {
            linkedChildren.forEach {
                it.onLinkedScroll(unconsumedX, unconsumedY, childConsumed)

                unconsumedX -= childConsumed[0]
                unconsumedY -= childConsumed[1]

                consumed[0] += childConsumed[0]
                consumed[1] += childConsumed[1]

                childConsumed[0] = 0
                childConsumed[1] = 0
            }
        }

    }

    private fun findLinkScrollChildren() {
        (0..(childCount - 1))
                .map { getChildAt(it) }
                .filterIsInstance<LinkedChild>()
                .filterTo(linkedChildren) { it.enableLinkedScroll }
    }

}