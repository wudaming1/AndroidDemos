package com.arise.demo.nestedscrolling

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by wudaming on 2018/4/13.
 */
class CustomRecyclerView : RecyclerView {

    private val scrollOffset = IntArray(2)
    private val location = IntArray(2)
    private var shouldChange = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    override fun onTouchEvent(e: MotionEvent): Boolean {
        val result: Boolean
        val action = e.actionMasked
        val vtev = MotionEvent.obtain(e)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                getLocationInWindow(location)
                shouldChange = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (shouldChange) {
                    changeScrollState()
                }
                val newLocation = IntArray(2)
                getLocationInWindow(newLocation)
                scrollOffset[0] += newLocation[0] - location[0]
                scrollOffset[1] += newLocation[1] - location[1]

                location[0] = newLocation[0]
                location[1] = newLocation[1]

                vtev.offsetLocation(scrollOffset[0].toFloat(), scrollOffset[1].toFloat())
            }
            MotionEvent.ACTION_CANCEL -> {
                shouldChange = false
                scrollOffset[0] = 0
                scrollOffset[1] = 0
                location[0] = 0
                location[1] = 0
            }
            MotionEvent.ACTION_UP -> {
                shouldChange = false
                scrollOffset[0] = 0
                scrollOffset[1] = 0
                location[0] = 0
                location[1] = 0
            }
        }

        result = super.onTouchEvent(vtev)
        vtev.recycle()
        return result

    }

    fun changeScrollState() {

        val cls = RecyclerView::class.java
        val mothod = cls.getDeclaredMethod("setScrollState", Int::class.java)
        mothod.isAccessible = true
        mothod.invoke(this, 1)
    }

}