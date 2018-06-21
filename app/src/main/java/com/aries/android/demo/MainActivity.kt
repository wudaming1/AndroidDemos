package com.aries.android.demo

import android.content.Context
import android.os.Bundle
import com.aries.android.demo.common.MyActivity
import com.aries.android.demo.compress.CompressActivity
import com.aries.android.demo.touch.TouchActivity
import com.aries.base.launch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MyActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showTitleBar(false)
        context = this
        goNestedScrolling.setOnClickListener { PageRoute.launch(this, PageRoute.NestedScrollingDemo) }

        goTouch.setOnClickListener { context.launch(TouchActivity::class.java) }

        goRecyclerDemo.setOnClickListener { PageRoute.launch(this, PageRoute.RecyclerViewDemo) }

        goMaterialDemo.setOnClickListener { PageRoute.launch(this, PageRoute.MaterialDemo) }

        goAnimDemo.setOnClickListener { PageRoute.launch(this, PageRoute.AnimDemo) }

        goService.setOnClickListener { PageRoute.launch(this, PageRoute.AIDLDemo) }

        goWidget.setOnClickListener { PageRoute.launch(this, PageRoute.WidgetDemo) }

        goCompress.setOnClickListener { context.launch(CompressActivity::class.java) }

        goCompress.performClick()

    }
}
