package com.arise.android.demo.headrecyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.android.demo.R
import com.arise.android.demo.nestedscrolling.NestedScrollingActivity
import com.arise.tools.register_annotation.RegisterPage
import kotlinx.android.synthetic.main.activity_head_recycler_view.*

@RegisterPage(name = "HeadRecyclerViewActivity")
class HeadRecyclerViewActivity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context,HeadRecyclerViewActivity::class.java)
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
