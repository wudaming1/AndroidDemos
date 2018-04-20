package com.aries.sdk.recyclerview.sticky

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.aries.sdk.recyclerview.R
import kotlinx.android.synthetic.main.activity_sticky.*

class StickyActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, StickyActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val data = mutableListOf<Pair<String, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky)
        init()
    }

    private fun init() {

        (0..99).forEach {
            val text = if (it % 10 == 0) getString(R.string.item_title) else "我是第${it % 10}个子项"
            val type = if (it % 10 == 0) SimpleAdapter.TYPE_TITLE else SimpleAdapter.TYPE_CONTENT
            data.add(text to type)
        }

        val simpleAdapter = SimpleAdapter()
        simpleAdapter.setData(data)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(StickyDecorator())
        recyclerView.adapter = simpleAdapter
    }
}
