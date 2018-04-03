package com.arise.sdk.headrecyclerview

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView

/**
 * Created by wudaming on 2018/4/3.
 */
class ZoomHead : ImageView, LinkedHead {


    private var maxScale = 1.5f

    private var originWidth = 0
    private var originHeight = 0

    private var originX = 0
    private var originY = 0

    private val rollbackAnim= ValueAnimator()

    override var enableLinkedScroll: Boolean = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAnim()
    }

    private fun initAnim() {
        rollbackAnim.addUpdateListener {
            val value = it.animatedValue as Float
            scale(value)
        }
        rollbackAnim.duration = 200
        rollbackAnim.interpolator = DecelerateInterpolator()
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

    override fun acceptScroll(axis: Int): Boolean {
        val result = axis and ViewCompat.SCROLL_AXIS_VERTICAL
        return result != 0
    }

    override fun onLinkedScroll(x: Int, y: Int, consumed: IntArray) {
        if (rollbackAnim.isRunning) {
            rollbackAnim.cancel()
        }
        val consumedVertical = y - zoomSelf(y)
        consumed[1] = consumedVertical
    }

    override fun onStopLinkedScroll() {
        rollbackAnim.setFloatValues(1f * layoutParams.height / originHeight, 1f)
        rollbackAnim.start()
    }


    private fun zoomSelf(increment: Int): Int {
        var release = 0
        when {
            layoutParams.height + increment > originHeight * maxScale -> {
                release = (layoutParams.height + increment - originHeight * maxScale).toInt()
                scale(maxScale)
            }
            layoutParams.height + increment < originHeight -> {
                release = (layoutParams.height + increment - originHeight)
                scale(1.0f)
            }
            else -> {
                val scale = (layoutParams.height + increment) * 1.0f / originHeight
                scale(scale)
            }
        }

        return release
    }

    private fun scale(rate: Float) {
        layoutParams.height = (originHeight * rate).toInt()
        layoutParams.width = (originWidth * rate).toInt()
        moveToOriginCenter()
        requestLayout()
    }

    private fun moveToOriginCenter() {
        val xOffset = (layoutParams.width - originWidth) / 2
        val yOffset = (layoutParams.height - originHeight) / 2

        val params = layoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            params.leftMargin = -xOffset
            params.topMargin = -yOffset
        }
    }
}