package com.arise.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * 嵌套滑动机制内置实现版本
 */
class NestedScrollingParent2View : LinearLayout, NestedScrollingParent2 {


    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    private val TAG = NestedScrollingParent2View::class.java.simpleName

    private val linkedChildren = ArrayList<LinkedChild>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        findLinkScrollChildren()

    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray?, type: Int) {
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