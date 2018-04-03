package com.arise.android.demo.headrecyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.android.demo.R
import com.arise.android.demo.nestedscrolling.NestedScrollingActivity
import kotlinx.android.synthetic.main.activity_zoom_head.*

class ZoomHeadActivity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context,ZoomHeadActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_head)
        initRecyclerView(recyclerView)
    }

}
