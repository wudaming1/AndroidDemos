package com.arise.android.demo.nestedscrolling

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arise.android.demo.R
import kotlinx.android.synthetic.main.activity_nested_scrolling.*

class NestedScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling)
        initList()



    }

    private fun initList() {
        val dataList = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14)
        val adapter = NestedScrollingChildView.SimpleAdapter()
        adapter.setData(dataList)

        nestedScrollingChild.layoutManager = LinearLayoutManager(this)
        nestedScrollingChild.adapter = adapter

    }
}
