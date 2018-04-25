package com.aries.sdk.recyclerview.grid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.aries.sdk.recyclerview.R
import com.aries.sdk.recyclerview.common.SimpleAdapter
import kotlinx.android.synthetic.main.activity_grid.*

class GridActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context,GridActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val data = mutableListOf<Pair<String, Int>>()
    private val simpleAdapter = SimpleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        init()
        initButton()
    }

    private fun init() {
        (0..99).forEach {
            val text = if (it % 10 == 0) getString(R.string.item_title) else "我是第${it % 10}个子项"
            val type = if (it % 10 == 0) SimpleAdapter.TYPE_TITLE else SimpleAdapter.TYPE_CONTENT
            data.add(text to type)
        }

        simpleAdapter.setData(data)
        recyclerView.layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = simpleAdapter

    }

    private fun initButton(){
        delete.setOnClickListener {
            data.removeAt(0)
            simpleAdapter.notifyDataSetChanged()
        }

        add.setOnClickListener {
            data.add(0,("新增" to SimpleAdapter.TYPE_CONTENT))
            simpleAdapter.notifyDataSetChanged()
        }
    }
}
