package com.aries.sdk.recyclerview.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aries.sdk.recyclerview.R

/**
 * Author wudaming
 * Created on 2018/4/19
 */

abstract class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup, id: Int) {
            val view = LayoutInflater.from(parent.context).inflate(id, parent, false)
        }
    }


    abstract fun bindText(text: String)

}

class TitleViewHolder(view: View) : SimpleViewHolder(view) {

    private val textView = view.findViewById<TextView>(R.id.title)

    override fun bindText(text: String) {
        textView.text = text
    }
}

class ContentViewHolder(view: View) : SimpleViewHolder(view) {

    private val textView = view.findViewById<TextView>(R.id.content)

    override fun bindText(text: String) {
        textView.text = text
    }
}