package com.arise.sdk.headrecyclerview

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.stretch_head_layout.view.*

/**
 * Created by wudaming on 2018/4/2.
 */
class StretchHead : RelativeLayout, LinkedHead {

    private var maxHeight = dip2px(100f)
    private var minHeight = dip2px(40f)
    private val minX = dip2px(20f)
    private var maxX = 0
    private var currentHeight = 0

    override var enableLinkedScroll: Boolean = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.StretchHead)
            maxHeight = typedArray.getDimensionPixelSize(R.styleable.StretchHead_maxHeight, dip2px(100f))
            minHeight = typedArray.getDimensionPixelSize(R.styleable.StretchHead_minHeight, dip2px(40f))
            typedArray.recycle()
        }
        init(context)
    }

    private fun init(context: Context) {
        enableLinkedScroll = true
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stretch_head_layout, this, true)
    }

    override fun acceptScroll(axis: Int): Boolean {
        val result = axis and ViewCompat.SCROLL_AXIS_VERTICAL
        return result != 0
    }

    override fun onLinkedScroll(x: Int, y: Int, consumed: IntArray) {
        val consumedVertical = y - changeHeight(y)
        consumed[1] = consumedVertical
    }

    /**
     * @return 未消费offset
     */
    private fun changeHeight(offsetY: Int): Int {
        val unconsumed = safeOffsetHeight(offsetY)

        if (unconsumed != offsetY) {
            requestLayout()
        }
        return unconsumed
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentHeight = h
        if (h != oldh){
            moveContent()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (maxX == 0){
            maxX = (width - title.width)/2
        }
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


    private fun moveContent() {
        val moveProgress = calculateMoveProgress()
        title.x = minX + (maxX - minX) * moveProgress
    }

    private fun calculateMoveProgress(): Float {
        return (currentHeight - minHeight) * 1f / (maxHeight - minHeight)
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}