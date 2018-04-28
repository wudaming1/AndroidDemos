package com.aries.demo.animation

import android.content.Context
import android.os.Bundle
import com.aries.base.BaseActivity
import com.aries.base.launch
import com.aries.demo.animation.translation.TranslationActivity
import kotlinx.android.synthetic.main.activity_anim.*

class AnimActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        initRoute(this)
    }

    private fun initRoute(context: Context) {
        button.setOnClickListener { context.launch(TranslationActivity::class.java) }
    }
}
