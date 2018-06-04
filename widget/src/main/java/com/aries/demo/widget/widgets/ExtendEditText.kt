package com.aries.demo.widget.widgets

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.aries.demo.widget.R
import kotlinx.android.synthetic.main.layout_extend_edit_text.view.*

/**
 * Author wudaming
 * Created on 2018/5/29
 */
class ExtendEditText : RelativeLayout {

    private var isChecked = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int)
            : super(context, attr, defStyleAttr) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attr, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_extend_edit_text, this, true)

        firstIcon.setOnClickListener {
            mEditText.setText("")
        }

        secondIcon.setOnClickListener { _ ->
            isChecked = !isChecked
            if (isChecked) {
                mEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                secondIcon.setImageResource(R.drawable.edit_text_hide)
            } else {
                mEditText.transformationMethod = null
                secondIcon.setImageResource(R.drawable.edit_text_show)
            }
        }
    }


}
