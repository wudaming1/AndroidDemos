package com.arise.demo.nestedscrolling.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_head_recycler_view.*

class HeadRecyclerViewActivity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, HeadRecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_head_recycler_view)

        init()
    }

    private fun init() {
        initRecyclerView(recyclerView)
    }
}
