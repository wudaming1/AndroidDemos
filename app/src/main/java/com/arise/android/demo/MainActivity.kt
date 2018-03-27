package com.arise.android.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arise.android.demo.nestedscrolling.NestedScrollingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goNestedScrolling.setOnClickListener {
            val intent = Intent(this,NestedScrollingActivity::class.java)
            startActivity(intent)
        }
    }
}
