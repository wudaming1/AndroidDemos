package com.aries.sdk.recyclerview

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aries.base.launch
import com.aries.sdk.recyclerview.grid.GridActivity
import com.aries.sdk.recyclerview.sticky.StickyActivity
import kotlinx.android.synthetic.main.activity_reycyler_view.*

class RecyclerViewActivity : AppCompatActivity() {


    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_reycyler_view)
        goSticky.setOnClickListener {
            context.launch(StickyActivity::class.java)
        }
        goGrid.setOnClickListener { context.launch(GridActivity::class.java) }
    }
}
