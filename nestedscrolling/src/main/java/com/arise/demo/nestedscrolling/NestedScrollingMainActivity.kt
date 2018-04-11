package com.arise.demo.nestedscrolling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arise.demo.nestedscrolling.activities.BuildInNestedActivity
import com.arise.demo.nestedscrolling.activities.HeadRecyclerViewActivity
import com.arise.demo.nestedscrolling.activities.InterfaceNestedActivity
import com.arise.demo.nestedscrolling.activities.ZoomHeadActivity
import kotlinx.android.synthetic.main.activity_nested_scrolling_main.*

class NestedScrollingMainActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, NestedScrollingMainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling_main)
        initRoute(this)
    }

    private fun initRoute(context: NestedScrollingMainActivity) {
        goZoom.setOnClickListener { ZoomHeadActivity.launch(context) }
        goHead.setOnClickListener { HeadRecyclerViewActivity.launch(context) }
        goBuildIn.setOnClickListener { BuildInNestedActivity.launch(context) }
        goInterfaceNested.setOnClickListener { InterfaceNestedActivity.launch(context) }
    }
}
