package com.arise.demo.nestedscrolling.assist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.demo.nestedscrolling.R

/**
 * Created by wudaming on 2018/4/12.
 */

class SimpleAdapter : RecyclerView.Adapter<SimpleViewHolder>() {

    private val data: MutableList<Int> = mutableListOf()

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val content = "这是第${data[position]}个ITEM"
        holder.setContent(content)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = inflate(parent, R.layout.scrolling_recycler_item, false)
        return SimpleViewHolder(view)
    }

    fun setData(array: IntArray) {
        data.clear()
        data.addAll(array.toTypedArray())
    }

    private fun inflate(parent: ViewGroup, int: Int, attachToRoot: Boolean): View {
        return LayoutInflater.from(parent.context).inflate(int, parent, attachToRoot)
    }

}