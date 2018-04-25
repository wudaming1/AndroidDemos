package com.aries.sdk.recyclerview.sticky

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aries.sdk.recyclerview.R
import com.aries.sdk.recyclerview.common.SimpleAdapter

/**
 * Author wudaming
 * Created on 2018/4/20
 */
class StickyDecorator : RecyclerView.ItemDecoration() {

    private var stickyView: View? = null
    private var flag = false
    private var measured = false


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

            flag = false

            if (stickyView == null) {
                stickyView = createStickyView(parent)
            }
            initStickyContent(stickyView!!, firstVisiblePosition)
            if (!measured) {
                measureStickyView(stickyView!!, parent, firstVisiblePosition)
            }
            layoutStickyView(stickyView!!, parent, layoutManager, c)
            drawStickyView(stickyView!!, c)
        }

    }

    private fun createStickyView(parent: RecyclerView): View {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.title_item, parent, false)
    }

    private fun initStickyContent(stickyView: View, position: Int) {
        val textView = stickyView.findViewById<TextView>(R.id.title)
        textView.text = "title $position"
    }

    private fun measureStickyView(stickyView: View, parent: RecyclerView, position: Int) {
        val firstVisibleItem = parent.findViewHolderForLayoutPosition(position).itemView
        val height = firstVisibleItem.height
        val width = firstVisibleItem.width
        val layoutParams = stickyView.layoutParams as RecyclerView.LayoutParams
        layoutParams.height = height
        layoutParams.width = width
        val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        stickyView.measure(widthSpec, heightSpec)
        measured = true
    }

    private fun layoutStickyView(stickyView: View, parent: RecyclerView
                                 , layoutManager: LinearLayoutManager
                                 , c: Canvas) {
        val layoutParams = stickyView.layoutParams as RecyclerView.LayoutParams
        layoutParams.leftMargin = 0
        val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val type = parent.adapter.getItemViewType(firstCompletelyVisibleItemPosition)

        if (type != SimpleAdapter.TYPE_TITLE) {
            layoutParams.topMargin = 0
            return
        }

        val nextStickyItem = parent.findViewHolderForLayoutPosition(firstCompletelyVisibleItemPosition).itemView
        val nextTopMargin = nextStickyItem.top
        val stickyTopMargin = Math.min(0, nextTopMargin - stickyView.measuredHeight)

        layoutParams.topMargin = stickyTopMargin
        translateCanvas(c, layoutParams.topMargin)

        stickyView.layout(parent.paddingLeft
                , parent.paddingTop
                , parent.paddingLeft + stickyView.measuredWidth
                , parent.paddingTop + stickyView.measuredHeight)
    }

    private fun drawStickyView(stickyView: View, c: Canvas) {
        stickyView.draw(c)
        if (flag) {
            c.restore()
            flag = false
        }
    }


    private fun translateCanvas(c: Canvas, offset: Int) {
        if (!flag) {
            c.save()
            flag = true
        }
        c.translate(0f, offset.toFloat())
    }

}