package com.aries.sdk.recyclerview.head

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aries.sdk.recyclerview.R

/**
 * Created by wudaming on 2018/4/9.
 */
class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.MyViewHolder>() {

    private val data: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scrolling_recycler_item,parent,  false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = "这是第${data[position]}个ITEM"
        holder.setContent(content)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(array: IntArray) {
        data.clear()
        data.addAll(array.toMutableList())
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

}

