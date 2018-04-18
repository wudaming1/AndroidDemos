package com.arise.demo.nestedscrolling

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.aries.base.utils.DensityUtils

/**
 * 关联滑动父控件，拉伸类型。
 */
class MutableRelativeLayout : RelativeLayout, LinkedChild {

    override var enableLinkedScroll: Boolean = true
    override var enableLinkedMove: Boolean = false
    private var maxHeight = DensityUtils.dip2px(100f)
    private var minHeight = DensityUtils.dip2px(40f)

    private var currentHeight = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context)
        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this
                    , R.styleable.MutableRelativeLayout)
            maxHeight = typedArray.getDimensionPixelSize(R.styleable.MutableRelativeLayout_maxHeight
                    , maxHeight)
            minHeight = typedArray.getDimensionPixelSize(R.styleable.MutableRelativeLayout_minHeight
                    , minHeight)

            enableLinkedMove = typedArray.getBoolean(R.styleable.MutableRelativeLayout_enableMove, true)

            enableLinkedScroll = typedArray.getBoolean(R.styleable.MutableRelativeLayout_enableLinkedScroll, true)

            typedArray.recycle()
        }

    }

    override fun onLinkedScroll(x: Int, y: Int, consumed: IntArray, type: Int) {
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
    private fun move(offsetY: Int): Int {
        if (!canMove()) {
            return offsetY
        }
        return if (offsetY >= 0) {
            moveUp(offsetY)
        } else {
            -moveDown(Math.abs(offsetY))
        }
    }

    private fun canMove(): Boolean {
        if (enableLinkedMove) {
            return visibility == View.VISIBLE
        }
        return false
    }

    private fun moveUp(distance: Int): Int {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        val bottom = params.height + params.topMargin
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
        val maxCanMove = Math.abs(params.topMargin)
        var unconsumed = distance
        if (maxCanMove in 1..distance) {
            unconsumed = distance - maxCanMove
        } else if (maxCanMove > distance) {
            unconsumed = 0
        }
        params.topMargin += distance - unconsumed

        return unconsumed

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        currentHeight = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.nested_scrolling_head_layout, this, true)
        findView()
        isNestedScrollingEnabled = true
    }

    private fun findView() {
    }

    /**
     * @param offset 上滑正，下滑负
     *
     * @return 未消费的距离
     */
    fun changeHeight(offset: Int): Int {
        val params = layoutParams as MarginLayoutParams
        var targetHeight = params.height - offset
        if (targetHeight < minHeight) {
            targetHeight = minHeight
        } else if (targetHeight > maxHeight) {
            targetHeight = maxHeight
        }
        val unconsumed = -(params.height - offset - targetHeight)
        params.height = targetHeight
        return unconsumed
    }

}