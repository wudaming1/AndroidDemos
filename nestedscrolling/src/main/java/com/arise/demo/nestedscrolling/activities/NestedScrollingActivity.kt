package com.arise.demo.nestedscrolling.activities

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arise.demo.nestedscrolling.assist.SimpleAdapter

/**
 * Created by wudaming on 2018/3/30.
 */
abstract class NestedScrollingActivity : AppCompatActivity() {


    protected fun initRecyclerView(view: RecyclerView) {
        val dataList = IntArray(100) { i: Int -> i }
        val adapter = SimpleAdapter()
        adapter.setData(dataList)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = adapter

    }

}