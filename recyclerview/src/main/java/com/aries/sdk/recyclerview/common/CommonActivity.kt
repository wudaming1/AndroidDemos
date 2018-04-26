package com.aries.sdk.recyclerview.common

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.aries.base.BaseActivity
import com.aries.sdk.recyclerview.R

abstract class CommonActivity : BaseActivity() {

    protected val data = mutableListOf<Pair<String, Int>>()
    protected val simpleAdapter = SimpleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun initRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        (0..99).forEach {
            val text = if (it % 10 == 0) getString(R.string.item_title) else "我是第${it % 10}个子项"
            val type = if (it % 10 == 0) SimpleAdapter.TYPE_TITLE else SimpleAdapter.TYPE_CONTENT
            data.add(text to type)
        }

        simpleAdapter.setData(data)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = simpleAdapter
    }
}
