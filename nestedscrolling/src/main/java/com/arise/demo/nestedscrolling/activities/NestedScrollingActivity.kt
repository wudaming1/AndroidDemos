package com.arise.demo.nestedscrolling.activities

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.arise.demo.nestedscrolling.NestedScrollingChildView

/**
 * Created by wudaming on 2018/3/30.
 */
abstract class NestedScrollingActivity : AppCompatActivity() {


    protected fun initRecyclerView(view: NestedScrollingChildView) {
        val dataList = IntArray(100) { i: Int -> i }
        val adapter = NestedScrollingChildView.SimpleAdapter()
        adapter.setData(dataList)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = adapter

    }

}