package com.arise.demo.nestedscrolling.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_stretch_head.*

class StretchHeadActivity : NestedScrollingActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, StretchHeadActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretch_head)

        initRecyclerView(nestedScrollingChild)
        nestedScrollingChild.isNestedScrollingEnabled = true
        nestedParent.scrollingView = nestedScrollingChild
    }


}
