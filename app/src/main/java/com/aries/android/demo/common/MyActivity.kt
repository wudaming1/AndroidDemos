package com.aries.android.demo.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.aries.android.demo.R
import com.aries.base.BaseActivity
import com.aries.base.utils.ImmersiveUtil
import kotlinx.android.synthetic.main.common_contain_layout.*

/**
 * Author wudaming
 * Created on 2018/6/21
 * 页面通用逻辑。
 */
abstract class MyActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.common_contain_layout)
        ImmersiveUtil.makeSpaceForImmersive(contentView)

        addListener()
    }

    private fun addListener() {
        leftArrow.setOnClickListener { onBackPress() }
    }

    open fun onBackPress() {
        finish()
    }

    override fun setContentView(layoutResID: Int) {
        container.removeAllViews()
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutResID, container, true)
    }

    override fun setContentView(view: View?) {
        view?.apply {
            container.removeAllViews()
            container.addView(this)
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        if (params == null) {
            setContentView(view)
        } else {
            if (params is FrameLayout.LayoutParams) {
                view?.apply {
                    container.removeAllViews()
                    container.addView(this, params)
                }
            } else {
                throw RuntimeException("params 必须是 FrameLayout.LayoutParams！")
            }
        }
    }

    protected fun initTitle(text: String) {
        pageTitle.text = text
    }

    protected fun initTitle(id: Int) {
        pageTitle.setText(id)
    }

    protected fun showTitleBar(show: Boolean) {
        titleBar.visibility = if (show) View.VISIBLE else View.GONE
    }


}