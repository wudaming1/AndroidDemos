package com.aries.sdk.recyclerview

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.aries.sdk.recyclerview.head.LinkedHead

/**
 * Created by wudaming on 2018/3/30.
 */
class LinkScrollParent : LinearLayout {

    private val linkedChildren = arrayListOf<LinkedHead>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        findLinkScrollChildren()
        isNestedScrollingEnabled = true
    }

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return (ViewCompat.SCROLL_AXIS_VERTICAL and nestedScrollAxes) != 0
    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray) {
        super.onNestedPreScroll(target, dx, dy, consumed)
        dispatchInternal(dx, dy, consumed)
    }

    override fun onStopNestedScroll(child: View?) {
        super.onStopNestedScroll(child)
        linkedChildren.forEach { it.onStopLinkedScroll() }
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        if(child is LinkedHead){
            refreshLinkedChildren()
        }
    }

    private fun refreshLinkedChildren() {
        linkedChildren.clear()
        findLinkScrollChildren()
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        if (child is LinkedHead)
            deleteLinkedChild(child)
    }

    private fun deleteLinkedChild(child: LinkedHead) {
        if (linkedChildren.contains(child) && child.enableLinkedScroll) {
            linkedChildren.remove(child)
        }
    }


    private fun dispatchInternal(dx: Int, dy: Int, consumed: IntArray) {

        val unconsumedX = dx - consumed[0]
        val unconsumedY = dy - consumed[1]
        val childrenConsume = intArrayOf(0, 0)

        dispatchToChildren(unconsumedX, unconsumedY, childrenConsume)

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
                .filterIsInstance<LinkedHead>()
                .filterTo(linkedChildren) { it.enableLinkedScroll }
    }


}