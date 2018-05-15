package com.aries.android.demo

import android.content.Context
import android.os.Bundle
import com.aries.android.demo.touch.TouchActivity
import com.aries.base.BaseActivity
import com.aries.base.launch
import com.aries.demo.animation.AnimActivity
import com.aries.demo.materialdesign.MaterialActivity
import com.aries.sdk.recyclerview.RecyclerViewActivity
import com.arise.demo.nestedscrolling.activities.NestedScrollingMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Page.activity.aa
        context = this
        goNestedScrolling.setOnClickListener { context.launch(NestedScrollingMainActivity::class.java) }

        goTouch.setOnClickListener { context.launch(TouchActivity::class.java) }

        goRecyclerDemo.setOnClickListener {

            context.launch(RecyclerViewActivity::class.java)
        }

        goMaterialDemo.setOnClickListener {
            context.launch(MaterialActivity::class.java)
        }

        goAnimDemo.setOnClickListener { context.launch(AnimActivity::class.java) }
    }
}
