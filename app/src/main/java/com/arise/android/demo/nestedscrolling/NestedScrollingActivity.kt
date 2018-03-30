package com.arise.android.demo.nestedscrolling

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by wudaming on 2018/3/30.
 */
abstract class NestedScrollingActivity : AppCompatActivity() {


    protected fun initRecyclerView(view: NestedScrollingChildView) {
        val dataList = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
        val adapter = NestedScrollingChildView.SimpleAdapter()
        adapter.setData(dataList)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = adapter

    }

}