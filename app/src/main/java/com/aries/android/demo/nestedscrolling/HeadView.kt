package com.aries.android.demo.nestedscrolling

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.aries.android.demo.DensityUtils
import com.aries.android.demo.R

/**
 * Created by wudaming on 2018/3/26.
 */
class HeadView : RelativeLayout {

    private val maxHeight = DensityUtils.dip2px(100f)
    private val minHeight = DensityUtils.dip2px(40f)
    private var currentHeight = 0


    private lateinit var TVtitle: TextView


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    /**
     * @return 未消费offset
     */
    fun changeHeight(offsetY: Int): Int {
        val unconsumed = safeOffsetHeight(offsetY)

        if (unconsumed != offsetY) {
            moveContent()
            requestLayout()
        }
        return unconsumed
    }

    fun canStretch():Boolean {
        val result = currentHeight < maxHeight

        return result
    }

    fun canShrink() = currentHeight > minHeight


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        currentHeight = h
        Log.e("HeadView","onSizeChanged:$currentHeight")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.nested_scrolling_head_layout, this, true)
        findView()
    }

    private fun findView() {
        TVtitle = findViewById(R.id.title)
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

    }


}