package com.arise.demo.nestedscrolling.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aries.base.launch
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_nested_scrolling_main.*

class NestedScrollingMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling_main)
        initRoute(this)
    }

    private fun initRoute(context: NestedScrollingMainActivity) {
        goStretchHead.setOnClickListener { context.launch(StretchHeadActivity::class.java) }
        goZoomHead.setOnClickListener { context.launch(ZoomHeadActivity::class.java) }
    }
}
