package com.aries.sdk.recyclerview.head

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.aries.base.MovableLine

/**
 * Created by wudaming on 2018/4/9.
 */
class CustomRecyclerView : RecyclerView {

    private val linkedChildren = arrayListOf<LinkedHead>()

    private val consume = intArrayOf(0, 0)

    private val touchPath = MovableLine()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            touchPath.reset()
            touchPath.moveTo(e.rawX.toInt(), e.rawY.toInt())
        }

        if (e.action == MotionEvent.ACTION_MOVE) {
            touchPath.moveTo(e.rawX.toInt(), e.rawY.toInt())
            if (touchPath.isReady()) {
                dispatchInternal(touchPath.getXOffset(), touchPath.getYOffset(), consume)
            }
        }
        if (e.action == MotionEvent.ACTION_UP) {
            touchPath.reset()
            linkedChildren.forEach { it.onStopLinkedScroll() }
        }
        val result = super.onTouchEvent(e)
        if (consume[1] < 0)
            scrollBy(consume[0], consume[1])
        consume[0] = 0
        consume[1] = 0
        return result
    }

    private fun init(context: Context) {
        findLinkScrollChildren()
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        if (child is LinkedHead) {
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

    private fun dispatchInternal(dx: Int, dy: Int, consumed: IntArray): Boolean {

        consumed[0] = 0
        consumed[1] = 0
        val childrenConsume = intArrayOf(0, 0)

        dispatchToChildren(dx, dy, childrenConsume)

        consumed[0] += childrenConsume[0]
        consumed[1] += childrenConsume[1]

        return consumed[0] != 0 || consumed[1] != 0
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