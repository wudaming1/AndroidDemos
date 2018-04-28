package com.aries.demo.animation.translation

import android.os.Bundle
import android.widget.Toast
import com.aries.base.BaseActivity
import com.aries.demo.animation.R
import kotlinx.android.synthetic.main.activity_translation.*

class TranslationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        init()
    }

    private fun init() {
        first.setOnClickListener { Toast.makeText(this, "first click", Toast.LENGTH_LONG).show() }

        moveDown.setOnClickListener {
            first.translationY += 5
            //可以触发位移，且位移并没有导致他后面的view的移动
//            first.requestLayout()

            //可以触发位移
            first.invalidate()
            //无论哪种，点击事件的接收点都变了。

        }
    }
}
