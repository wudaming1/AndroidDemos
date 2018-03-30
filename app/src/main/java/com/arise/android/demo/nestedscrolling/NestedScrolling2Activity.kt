package com.arise.android.demo.nestedscrolling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.android.demo.R
import kotlinx.android.synthetic.main.activity_nested_scrolling2.*

class NestedScrolling2Activity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, NestedScrolling2Activity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling2)
        initRecyclerView(nestedScrollingChild)
    }


}
