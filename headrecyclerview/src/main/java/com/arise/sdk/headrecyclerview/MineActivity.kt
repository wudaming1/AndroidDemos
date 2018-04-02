package com.arise.sdk.headrecyclerview

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MineActivity : AppCompatActivity() {

    companion object {
        fun launch(context:Context){
            val intent = Intent(context,MineActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)
    }
}
