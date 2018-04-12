package com.arise.demo.nestedscrolling

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.aries.base.utils.DensityUtils

/**
 * Created by wudaming on 2018/3/26.
 */
class HeadView : RelativeLayout, LinkedChild {

    override var enableLinkedScroll: Boolean = true

    override var enableLinkedMove: Boolean = false

    private val maxHeight = DensityUtils.dip2px(100f)
    private val minHeight = DensityUtils.dip2px(40f)
    private var currentHeight = 0

    private var enableMove = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    override fun onLinkedScroll(x: Int, y: Int, consumed: IntArray) {
        consumed[0] = 0
        var unconsumedY: Int
        if (y > 0) {
            //向上滑动手指
            unconsumedY = changeHeight(y)
            unconsumedY = move(unconsumedY)
        } else {
            //向下滑动手指
            unconsumedY = move(y)
            unconsumedY = changeHeight(unconsumedY)
        }
        consumed[1] = y - unconsumedY
        if (consumed[1] != 0) {
            requestLayout()
        }
    }

    /**
     * @param offsetY 上滑正，下滑负
     *
     * @return 未消费offset
     */
    fun changeHeight(offsetY: Int): Int {
        val unconsumed = -safeOffsetHeight(-offsetY)


        return unconsumed
    }


    /**
     * @param offsetY 上滑正，下滑负
     *
     * @return 未消费offset
     */
    private fun move(offsetY: Int): Int {
        return if (offsetY >= 0) {
            moveUp(offsetY)
        } else {
            -moveDown(Math.abs(offsetY))
        }
    }

    private fun moveUp(distance: Int): Int {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        val bottom = height + params.topMargin
        var unconsumed = distance
        if (bottom in 1..distance) {
            unconsumed = distance - bottom
        } else if (bottom > distance) {
            unconsumed = 0
        }

        params.topMargin -= distance - unconsumed
        return unconsumed

    }

    private fun moveDown(distance: Int): Int {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        val maxCanMove = -params.topMargin
        var unconsumed = distance
        if (maxCanMove in 1..distance) {
            unconsumed = distance - maxCanMove
        } else if (maxCanMove > distance) {
            unconsumed = 0
        }
        params.topMargin += distance - unconsumed

        return unconsumed

    }

    fun canStretch(): Boolean {

        return currentHeight < maxHeight
    }

    fun canShrink() = currentHeight > minHeight

    private fun canMove(): Boolean {
        if (enableMove) {
            return y < 0 && visibility == View.VISIBLE
        }
        return false
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        currentHeight = h
        Log.e("HeadView", "onSizeChanged:$currentHeight")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.nested_scrolling_head_layout, this, true)
        findView()
    }

    private fun findView() {
    }

    /**
     * @return 未消费的距离
     */
    private fun safeOffsetHeight(offset: Int): Int {
        var safeHeight = offset + height
        if (safeHeight < minHeight) {
            safeHeight = minHeight
        } else if (safeHeight > maxHeight) {
            safeHeight = maxHeight
        }
        layoutParams?.apply {
            layoutParams.height = safeHeight
        }
        return offset + height - safeHeight
    }


}