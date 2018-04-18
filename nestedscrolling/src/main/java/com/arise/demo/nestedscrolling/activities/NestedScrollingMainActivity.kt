package com.arise.demo.nestedscrolling.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arise.demo.nestedscrolling.R
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
        goStretchHead.setOnClickListener { StretchHeadActivity.launch(context) }
        goZoomHead.setOnClickListener { ZoomHeadActivity.launch(context) }
    }
}
