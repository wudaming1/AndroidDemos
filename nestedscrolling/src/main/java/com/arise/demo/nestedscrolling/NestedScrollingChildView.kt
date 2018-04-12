package com.arise.demo.nestedscrolling

import android.content.Context
import android.support.v4.view.NestedScrollingChild
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


fun inflate(parent: ViewGroup, int: Int, attachToRoot: Boolean): View {
    return LayoutInflater.from(parent.context).inflate(int, parent, attachToRoot)
}

/**
 * 嵌套滑动机制接口实现版本，使用时通用的
 */
class NestedScrollingChildView : RecyclerView, NestedScrollingChild {

    private val TAG = NestedScrollingChildView::class.java.simpleName

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        isNestedScrollingEnabled = true
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
        if (canScrollVertically(-1) && dy < 0) {
            return false
        }
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
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
            val view = inflate(parent, R.layout.scrolling_recycler_item, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val content = "这是第${data[position]}个ITEM"
            holder.setContent(content)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setData(array: IntArray) {
            data.clear()
            data.addAll(array.toTypedArray())
        }

    }

}