package com.aries.sdk.recyclerview.learning

import android.os.Bundle
import com.aries.sdk.recyclerview.R
import com.aries.sdk.recyclerview.common.CommonActivity
import kotlinx.android.synthetic.main.activity_learning.*

class LearningActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)
        initRecyclerView(recyclerView, CustomLayoutManager())
    }
}
