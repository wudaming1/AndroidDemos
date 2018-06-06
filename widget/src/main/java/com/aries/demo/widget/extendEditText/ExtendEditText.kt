package com.aries.demo.widget.extendEditText

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
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

    companion object {
        private val formatTypeArray = arrayOf(FormatType.NORMAL
                , FormatType.PHONE
                , FormatType.BANK)
    }

    private var isChecked = false
    private var hasFocus = false
    private var isEmpty = true
    private var isPassword = false
    private var numberFormatter: NumberFormatter = PhoneFormatter()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int)
            : super(context, attr, defStyleAttr) {
        init(context)
        attr?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.ExtendEditText)
            val index = typedArray.getInt(R.styleable.ExtendEditText_formatType, -1)
            if (index > -1)
                numberFormatter = NumberFormatterFactory.createNumberFormatter(formatTypeArray[index])
            typedArray.recycle()
        }

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

    fun setFormatter(numberFormatter: NumberFormatter) {
        this.numberFormatter = numberFormatter
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        mEditText.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        mEditText.removeTextChangedListener(watcher)
    }

    fun setFilter(vararg filters: InputFilter) {
        mEditText.filters = filters
    }

    fun getTextView() = mEditText

    override fun afterTextChanged(s: Editable?) {
        isEmpty = s.isNullOrEmpty()
        refreshRightIcons()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.e("wdm", "$s,start:$start,before:$before,count$count")
        val formattedText = numberFormatter.getFormattedText(s.toString(), " ")
        if (formattedText != s.toString()) {
            mEditText.setText(formattedText)
            mEditText.setSelection(formattedText.length)
        }

    }

    private fun refreshRightIcons() {
        firstIcon.visibility = if (!isEmpty && hasFocus) View.VISIBLE else View.GONE
        secondIcon.visibility = if (!isEmpty && hasFocus && isPassword) View.VISIBLE
        else View.GONE

    }
}
