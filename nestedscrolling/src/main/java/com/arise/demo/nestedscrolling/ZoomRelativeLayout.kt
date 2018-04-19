package com.arise.demo.nestedscrolling

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout

/**
 * Author wudaming
 * Created on 2018/4/18
 */
class ZoomRelativeLayout : RelativeLayout, LinkedChild {


    override var enableLinkedScroll: Boolean = true
    override var enableLinkedMove: Boolean = true

    private var maxScale = 1.5f
    private var minScale = 1.0f

    private var originWidth = 0
    private var originHeight = 0

    private var originX = 0
    private var originY = 0

    private val rollbackAnim = ValueAnimator()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {

        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.ZoomRelativeLayout)
            maxScale = typedArray.getFloat(R.styleable.ZoomRelativeLayout_maxScale, maxScale)
            minScale = typedArray.getFloat(R.styleable.ZoomRelativeLayout_minScale, minScale)
            enableLinkedMove = typedArray.getBoolean(R.styleable.ZoomRelativeLayout_enableMove, true)
            enableLinkedScroll = typedArray.getBoolean(R.styleable.ZoomRelativeLayout_enableLinkedScroll, true)
            typedArray.recycle()
        }

        initAnim()
    }

    override fun acceptScroll(axes: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onLinkedScroll(x: Int, y: Int, consumed: IntArray, type: Int) {
        var unconsumedY: Int
        if (y > 0) {
            unconsumedY = zoomIfNeed(y, type)
            unconsumedY = move(unconsumedY)

        } else {
            unconsumedY = move(y)
            unconsumedY = zoomIfNeed(unconsumedY, type)
        }

        consumed[1] = y - unconsumedY
        if (consumed[1] != 0) {
            requestLayout()
        }
    }

    override fun onStopLinkedScroll() {
        rollbackAnim.setFloatValues(1f * layoutParams.height / originHeight, 1f)
        rollbackAnim.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (originWidth == 0) {
            originHeight = h
            originWidth = w
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (originX == 0) {
            originX = left
            originY = top
        }
    }

    private fun initAnim() {
        rollbackAnim.addUpdateListener {
            val value = it.animatedValue as Float
            scale(value)
            requestLayout()
        }
        rollbackAnim.duration = 200
        rollbackAnim.interpolator = DecelerateInterpolator()
    }

    private fun zoomIfNeed(increment: Int, type: Int): Int {
        if (type == ViewCompat.TYPE_TOUCH && increment != 0) {
            if (rollbackAnim.isRunning) {
                rollbackAnim.cancel()
            }
            return zoomSelf(increment)
        }

        return increment

    }

    private fun zoomSelf(increment: Int): Int {
        var release = 0
        when {
            layoutParams.height - increment > originHeight * maxScale -> {
                release = (increment - layoutParams.height + originHeight * maxScale).toInt()
                scale(maxScale)
            }
            layoutParams.height - increment < originHeight * minScale -> {
                release = (increment - layoutParams.height + originHeight * minScale).toInt()
                scale(minScale)
            }
            else -> {
                val scale = (layoutParams.height - increment) * 1.0f / originHeight
                scale(scale)
            }
        }

        return release
    }

    private fun scale(rate: Float) {
        layoutParams.height = (originHeight * rate).toInt()
        layoutParams.width = (originWidth * rate).toInt()
        moveToHorizCenter()
    }

    private fun moveToHorizCenter() {
        val xOffset = (layoutParams.width - originWidth) / 2

        val params = layoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            params.leftMargin = -xOffset
        }
    }

    private fun canMove(): Boolean {
        if (enableLinkedMove) {
            return visibility == View.VISIBLE
        }
        return false
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
}