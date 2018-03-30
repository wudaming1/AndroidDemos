package com.arise.android.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arise.android.demo.nestedscrolling.NestedScrollingActivity2
import com.arise.android.demo.nestedscrolling.NestedScrollingActivity1
import com.arise.android.demo.touch.TouchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goNestedScrolling.setOnClickListener { NestedScrollingActivity1.launch(this@MainActivity) }

        goTouch.setOnClickListener { TouchActivity.launch(this@MainActivity) }

        goNestedScrolling2.setOnClickListener { NestedScrollingActivity2.launch(this@MainActivity) }
    }
}
