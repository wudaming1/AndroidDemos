package com.aries.sdk.recyclerview.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.aries.sdk.recyclerview.R
import com.aries.sdk.recyclerview.head.AdapterWrapper
import com.aries.sdk.recyclerview.head.CustomRecyclerView
import com.aries.sdk.recyclerview.head.SimpleAdapter
import com.aries.sdk.recyclerview.head.ZoomHead
import kotlinx.android.synthetic.main.activity_head_demo.*

class HeadDemoActivity : AppCompatActivity() {

    companion object {
        fun launch(context:Context){
            val intent = Intent(context, HeadDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var stretchHead:ZoomHead

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_head_demo)
        initHeadView()
        initRecycler(recyclerView)

    }

    private fun initHeadView() {
        stretchHead = ZoomHead(this)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                600)
        stretchHead.layoutParams = layoutParams
        stretchHead.setImageResource(R.mipmap.head_bg)
        stretchHead.scaleType = ImageView.ScaleType.FIT_XY
    }

    private fun initRecycler(view: CustomRecyclerView){
        val list = (0..100).toList()
        val adapter = SimpleAdapter()
        adapter.setData(list.toIntArray())
        val wrapAdapter = AdapterWrapper(adapter)
        wrapAdapter.addHeaderView(stretchHead)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = wrapAdapter
    }
}
