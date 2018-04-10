package com.aries.android.demo.nestedscrolling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aries.android.demo.R
import kotlinx.android.synthetic.main.activity_nested_scrolling.*

class NestedScrollingActivity1 : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, NestedScrollingActivity1::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling)

        initRecyclerView(nestedScrollingChild)
    }

}
