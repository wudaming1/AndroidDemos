package com.aries.sdk.recyclerview

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aries.base.launch
import com.aries.sdk.recyclerview.grid.GridActivity
import com.aries.sdk.recyclerview.learning.LearningActivity
import com.aries.sdk.recyclerview.sticky.StickyActivity
import kotlinx.android.synthetic.main.activity_reycyler_view.*

class RecyclerViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reycyler_view)
        initRoute(this)
    }

    private fun initRoute(context: Context) {
        goSticky.setOnClickListener { context.launch(StickyActivity::class.java) }
        goGrid.setOnClickListener { context.launch(GridActivity::class.java) }
        goLearning.setOnClickListener { context.launch(LearningActivity::class.java) }
    }
}
