package com.aries.demo.widget.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.aries.demo.widget.R
import kotlinx.android.synthetic.main.layout_extend_edit_text.view.*

/**
 * Author wudaming
 * Created on 2018/5/29
 */
class ExtendEditText : RelativeLayout, TextWatcher {

    private var isChecked = false
    private var hasFocus = false
    private var isEmpty = true
    private var isPassword = false

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

        mEditText.setOnFocusChangeListener { _, hasFocus ->
            this.hasFocus = hasFocus
            refreshRightIcons()
        }

        mEditText.addTextChangedListener(this)
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        mEditText.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        mEditText.removeTextChangedListener(watcher)
    }

    override fun afterTextChanged(s: Editable?) {
        isEmpty = s.isNullOrEmpty()
        refreshRightIcons()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun refreshRightIcons() {
        firstIcon.visibility = if (!isEmpty && hasFocus) View.VISIBLE else View.GONE
        secondIcon.visibility = if (!isEmpty && hasFocus && isPassword) View.VISIBLE
        else View.GONE

    }
}
