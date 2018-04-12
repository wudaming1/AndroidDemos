package com.arise.demo.nestedscrolling.assist

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.arise.demo.nestedscrolling.R

/**
 * Created by wudaming on 2018/4/12.
 */
class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val content: TextView = view.findViewById(R.id.content)

//    init {
//            content.setOnClickListener {
//                Toast.makeText(scrollingView.context,
//                        makeToastContent(),
//                        Toast.LENGTH_SHORT)
//                        .show()
//            }
//    }

    fun setContent(content: String) {
        this.content.text = content
    }

    private fun makeToastContent(): String {
        return if (TextUtils.isEmpty(content.text)) "no content" else content.text.toString()
    }
}