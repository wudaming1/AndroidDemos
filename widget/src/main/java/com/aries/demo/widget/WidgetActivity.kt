package com.aries.demo.widget

import android.os.Bundle
import com.aries.base.BaseActivity
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)

        firstEditText.setFilter()
    }
}
