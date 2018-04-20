package com.aries.sdk.recyclerview.sticky

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aries.sdk.recyclerview.R

/**
 * Author wudaming
 * Created on 2018/4/19
 */

class SimpleAdapter : RecyclerView.Adapter<SimpleViewHolder>() {
    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_CONTENT = 1

    }

    private val data = mutableListOf<Pair<String, Int>>()

    fun setData(data: List<Pair<String, Int>>) {
        this.data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        when (viewType) {
            TYPE_CONTENT -> {
                val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.content_item, parent, false)
                return ContentViewHolder(view)
            }
            TYPE_TITLE -> {
                val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.title_item, parent, false)
                return TitleViewHolder(view)
            }
        }
        throw RuntimeException("Unknown viewType:$viewType")
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val text = data[position].first
        holder.bindText(text)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].second
    }
}