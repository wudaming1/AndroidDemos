package com.arise.demo.nestedscrolling

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.OverScroller

/**
 * 嵌套滑动机制内置实现版本
 */
class NestedScrollingParentView : LinearLayout, NestedScrollingParent2, Runnable {

    private val TAG = NestedScrollingParentView::class.java.simpleName

    var scrollingView: View? = null

    private val INVALID_POINTER = -1

    private var mLastTouchX = 0
    private var mLastTouchY = 0

    private var mLastScrollX = 0
    private var mLastScrollY = 0

    private var mScrollPointerId = INVALID_POINTER

    private val mVelocityTracker = VelocityTracker.obtain()

    private lateinit var scroller: OverScroller

    private lateinit var viewConfig: ViewConfiguration

    private val linkedChildren = ArrayList<LinkedChild>()

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        cancelFling()
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        scrollingView = target
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray?, type: Int) {

        if (target.canScrollVertically(-1) && dy < 0) {
            return
        }
        val safeConsumed = IntArray(2)
        dispatchToChildren(dx, dy, safeConsumed, type)
        if (consumed != null) {
            consumed[0] = safeConsumed[0]
            consumed[1] = safeConsumed[1]
        }

    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        dispatchStop()
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        findLinkScrollChildren()
        scroller = OverScroller(context)
        viewConfig = ViewConfiguration.get(context)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.actionMasked
        val actionIndex = e.actionIndex
        val event = MotionEvent.obtain(e)
        var eventAddedToVelocityTracker = false
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                recordEventMessage(event, 0)
                cancelFling()
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                recordEventMessage(event, actionIndex)
            }

            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id "
                            + mScrollPointerId + " not found. Did any MotionEvents get skipped?")
                    return false
                }

                val x = (event.getX(index) + 0.5f).toInt()
                val y = (event.getY(index) + 0.5f).toInt()
                val dx = mLastTouchX - x
                val dy = mLastTouchY - y
                dispatchInternal(dx, dy, ViewCompat.TYPE_TOUCH)
                mLastTouchX = x
                mLastTouchY = y
            }

            MotionEvent.ACTION_POINTER_UP -> onPointerUp(event)

            MotionEvent.ACTION_UP -> {
                mVelocityTracker.addMovement(event)
                eventAddedToVelocityTracker = true
                startFling()
                dispatchStop()
                mVelocityTracker.clear()
            }
            MotionEvent.ACTION_CANCEL -> {
                dispatchStop()
                mVelocityTracker.clear()
            }

        }
        if (!eventAddedToVelocityTracker) {
            mVelocityTracker.addMovement(event)
        }
        event.recycle()
        return true
    }

    private fun startFling() {
        mVelocityTracker.computeCurrentVelocity(1000, viewConfig.scaledMaximumFlingVelocity.toFloat())
        val velocity = getVelocityY()
        if (velocity != 0) {
            scroller.fling(0, 0, 0, velocity
                    , Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE)
            postOnAnimation(this)
        } else {
            dispatchStop()
        }
    }

    private fun cancelFling() {
        scroller.forceFinished(true)
        mLastScrollX = 0
        mLastScrollY = 0
    }

    private fun onPointerUp(e: MotionEvent) {
        val actionIndex = e.actionIndex
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            val newIndex = if (actionIndex == 0) 1 else 0
            recordEventMessage(e, newIndex)
        }
    }

    private fun recordEventMessage(e: MotionEvent, actionIndex: Int) {
        mScrollPointerId = e.getPointerId(actionIndex)
        mLastTouchX = (e.getX(actionIndex) + 0.5f).toInt()
        mLastTouchY = (e.getY(actionIndex) + 0.5f).toInt()
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

    private fun dispatchInternal(dx: Int, dy: Int, type: Int) {
        var dx1 = dx
        var dy1 = dy
        val mScrollConsumed = IntArray(2)
        dispatchToChildren(dx, dy, mScrollConsumed, type)
        dx1 -= mScrollConsumed[0]
        dy1 -= mScrollConsumed[1]
        if (dx1 != 0 || dy1 != 0) {
            scrollingView?.apply { this.scrollBy(dx1, dy1) }
        }
    }

    private fun dispatchToChildren(dx: Int, dy: Int, consumed: IntArray, type: Int) {
        var unconsumedX = dx
        var unconsumedY = dy
        val childConsumed = IntArray(2)
        if (linkedChildren.isNotEmpty()) {
            linkedChildren.forEach {
                it.onLinkedScroll(unconsumedX, unconsumedY, childConsumed, type)

                unconsumedX -= childConsumed[0]
                unconsumedY -= childConsumed[1]

                consumed[0] += childConsumed[0]
                consumed[1] += childConsumed[1]

                childConsumed[0] = 0
                childConsumed[1] = 0
            }
        }

    }

    private fun dispatchStop() {
        if (linkedChildren.isNotEmpty()) {
            linkedChildren.forEach {
                it.onStopLinkedScroll()
            }
        }
    }

    private fun findLinkScrollChildren() {
        (0..(childCount - 1))
                .map { getChildAt(it) }
                .filterIsInstance<LinkedChild>()
                .filterTo(linkedChildren) { it.enableLinkedScroll }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracker.recycle()
        super.onDetachedFromWindow()
    }

    override fun run() {
        if (scroller.computeScrollOffset()) {
            val x = scroller.currX
            val y = scroller.currY
            dispatchInternal(mLastScrollX - x, mLastScrollY - y, ViewCompat.TYPE_NON_TOUCH)
            mLastScrollX = x
            mLastScrollY = y

            postOnAnimation(this)
        } else {
            dispatchStop()
            mLastScrollY = 0
            mLastScrollX = 0
        }
    }

    private fun getVelocityY(): Int {
        val velocityY: Int
        val rawVelocityY = mVelocityTracker.getYVelocity(mScrollPointerId)
        velocityY = if (Math.abs(rawVelocityY) < viewConfig.scaledMinimumFlingVelocity) {
            0
        } else {
            Math.max(-viewConfig.scaledMaximumFlingVelocity,
                    Math.min(rawVelocityY.toInt(), viewConfig.scaledMaximumFlingVelocity))
        }
        return velocityY
    }

}