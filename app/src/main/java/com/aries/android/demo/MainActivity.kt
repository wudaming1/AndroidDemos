package com.aries.android.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aries.android.demo.headrecyclerview.ZoomHeadActivity
import com.aries.android.demo.nestedscrolling.NestedScrollingActivity2
import com.aries.android.demo.nestedscrolling.NestedScrollingActivity1
import com.aries.android.demo.touch.TouchActivity
import com.aries.sdk.recyclerview.activitys.HomeActivity
import com.aries.tools.pageregister.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goNestedScrolling.setOnClickListener { NestedScrollingActivity1.launch(this@MainActivity) }

        goTouch.setOnClickListener { TouchActivity.launch(this@MainActivity) }

        goNestedScrolling2.setOnClickListener { NestedScrollingActivity2.launch(this@MainActivity) }

        goHeadRecyclerView.setOnClickListener {
            Router.goPage(Page.activity.HeadRecyclerViewActivity,this@MainActivity)
//            HeadRecyclerViewActivity.launch(this@MainActivity)
        }

        goZoomView.setOnClickListener {
            ZoomHeadActivity.launch(this@MainActivity)
        }

        goRecyclerDemo.setOnClickListener { HomeActivity.launch(this@MainActivity) }
    }
}
