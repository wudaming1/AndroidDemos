package com.aries.sdk.recyclerview.activitys

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aries.sdk.recyclerview.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context){
            val intent = Intent(context,HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textView.setOnClickListener { HeadDemoActivity.launch(this@HomeActivity) }
    }
}
