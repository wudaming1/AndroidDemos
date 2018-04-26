package com.arise.demo.nestedscrolling.activities

import android.os.Bundle
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_stretch_head.*

class StretchHeadActivity : NestedScrollingActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretch_head)

        initRecyclerView(nestedScrollingChild)
        nestedScrollingChild.isNestedScrollingEnabled = true
        nestedParent.scrollingView = nestedScrollingChild
    }


}
