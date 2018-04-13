package com.arise.demo.nestedscrolling.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_nested_scrolling2.*

class BuildInNestedActivity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, BuildInNestedActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling2)

        initRecyclerView(nestedScrollingChild)
        nestedScrollingChild.isNestedScrollingEnabled = true
    }


}
