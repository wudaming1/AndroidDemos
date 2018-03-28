package com.arise.android.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.*
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.arise.android.demo.MovableLine
import com.arise.android.demo.R


fun inflate(parent: ViewGroup, int: Int, attachToRoot: Boolean): View {
    return LayoutInflater.from(parent.context).inflate(int, parent, attachToRoot)
}

/**
 * Created by wudaming on 2018/3/21.
 */
class NestedScrollingChildView : RecyclerView, NestedScrollingChild {

    private val TAG = NestedScrollingChildView::class.java.simpleName

    private val mChildHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    private val consume = intArrayOf(0, 0)

    private val touchPath = MovableLine()

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        Log.e(TAG, "constructor")
        init()
    }

    /**
     * 在child没有消费事件前，分发事件，parent会收到onNestedPreScroll通知，
     */
    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {

        var result: Boolean = mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
        //传入的consumed可能为空，经过传递后，如果进入onNestedPreScroll流程，consumed一定不为空

//        consumed?.apply {
//            result = dispatchNestedScroll(consumed[0], consumed[1], dx - consumed[0],
//                    dy - consumed[1], offsetInWindow) || result
//        }
        return result
    }


    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        Log.e(TAG, "dispatchNestedScroll")
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }


    private fun init() {
        mChildHelper.isNestedScrollingEnabled = true
    }

    override fun stopNestedScroll() {
        Log.e(TAG, "stopNestedScroll")
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
//        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.e(TAG, "startNestedScroll")
        return mChildHelper.startNestedScroll(axes)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            touchPath.reset()
            touchPath.moveTo(e.rawX.toInt(), e.rawY.toInt())
        }

        if (e.action == MotionEvent.ACTION_MOVE) {
            touchPath.moveTo(e.rawX.toInt(), e.rawY.toInt())
//            Log.e("before","offset:${touchPath.getYOffset()},isReady:${touchPath.isReady()}," +
//                    "canScrollVertically:${canScrollVertically(-1)}")
            if (touchPath.isReady() && startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
                    && !canScrollVertically(-1)) {
//                Log.e("ming","offset:${touchPath.getYOffset()}")
                if (dispatchNestedPreScroll(touchPath.getXOffset(), touchPath.getYOffset(), consume, null)) {
                    return true
                }
            }
        }
        if (e.action == MotionEvent.ACTION_UP){
            touchPath.reset()
        }

        return super.onTouchEvent(e)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val content: TextView = view.findViewById(R.id.content)

        init {
//            content.setOnClickListener {
//                Toast.makeText(view.context,
//                        makeToastContent(),
//                        Toast.LENGTH_SHORT)
//                        .show()
//            }
        }

        fun setContent(content: String) {
            this.content.text = content
        }

        private fun makeToastContent(): String {
            return if (TextUtils.isEmpty(content.text)) "no content" else content.text.toString()
        }
    }

    class SimpleAdapter : RecyclerView.Adapter<ViewHolder>() {

        private val data: MutableList<Int> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflate(parent, R.layout.nested_scrolling_recycler_item, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val content = "这是第${data[position]}个ITEM"
            holder.setContent(content)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setData(array: Array<Int>) {
            data.clear()
            data.addAll(array)
        }

    }

}