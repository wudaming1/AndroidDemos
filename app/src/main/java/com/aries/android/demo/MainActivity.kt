package com.aries.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aries.android.demo.touch.TouchActivity
import com.aries.demo.materialdesign.MaterialActivity
import com.aries.sdk.recyclerview.RecyclerViewActivity
import com.arise.demo.nestedscrolling.activities.NestedScrollingMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goNestedScrolling.setOnClickListener { NestedScrollingMainActivity.launch(this@MainActivity) }

        goTouch.setOnClickListener { TouchActivity.launch(this@MainActivity) }

        goRecyclerDemo.setOnClickListener {
            RecyclerViewActivity.launch(this@MainActivity)
        }

        goMaterialDemo.setOnClickListener {
            MaterialActivity.launch(this@MainActivity)
        }
    }
}
