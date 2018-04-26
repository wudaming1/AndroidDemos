package com.arise.demo.nestedscrolling.activities

import android.os.Bundle
import com.arise.demo.nestedscrolling.R
import kotlinx.android.synthetic.main.activity_zoom_head.*

class ZoomHeadActivity : NestedScrollingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_head)

        initRecyclerView(nestedScrollingChild)
    }

}
