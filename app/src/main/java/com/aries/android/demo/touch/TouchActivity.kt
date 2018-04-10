package com.aries.android.demo.touch

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aries.android.demo.R

class TouchActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context,TouchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
    }
}
