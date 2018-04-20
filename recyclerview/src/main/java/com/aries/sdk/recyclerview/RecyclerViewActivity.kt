package com.aries.sdk.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aries.sdk.recyclerview.sticky.StickyActivity
import kotlinx.android.synthetic.main.activity_reycyler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_reycyler_view)
        goSticky.setOnClickListener { StickyActivity.launch(context) }
    }
}
