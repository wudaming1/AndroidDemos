package com.aries.demo.widget.extendEditText

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.RelativeLayout
import com.aries.demo.widget.R

/**
 * Author wudaming
 * Created on 2018/5/29
 * 不支持wrap_content
 */
class ExtendEditText : RelativeLayout, TextWatcher {

    companion object {
        private val formatTypeArray = arrayOf(FormatType.NORMAL
                , FormatType.PHONE
                , FormatType.BANK)

        private const val CLEAR_ICON_ID = 1027384
    }

    private var isChecked = false
    private var hasFocus = false
    private var isEmpty = true
    private var separator = " "
    private var numberFormatter: NumberFormatter = NoFormatter()
    private val mEditText: AutoCompleteTextView
    private var firstIcon: ImageView? = null
    private var secondIcon: ImageView? = null
    private var stateListDrawable: StateListDrawable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int)
            : super(context, attr, defStyleAttr) {

        mEditText = AutoCompleteTextView(context, attr)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mEditText, params)

        readAttrs(attr)

        init()
        //移除和AutoCompleteTextView都有的属性，避免重复。
        background = null
        setPadding(0, 0, 0, 0)
    }

    private fun readAttrs(attr: AttributeSet?) {
        attr?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.ExtendEditText)

            val index = typedArray.getInt(R.styleable.ExtendEditText_formatType, -1)
            if (index > -1)
                numberFormatter = NumberFormatterFactory.createNumberFormatter(formatTypeArray[index])
            if (index > 0) {
                mEditText.inputType = InputType.TYPE_CLASS_NUMBER
            }

            typedArray.getDrawable(R.styleable.ExtendEditText_clear_icon)?.apply {
                initClearIcon(this)
            }

            typedArray.getDrawable(R.styleable.ExtendEditText_hide_icon)?.apply {
                initPasswordIcon(this)
                if (this is StateListDrawable) {
                    stateListDrawable = this
                    this.setState(intArrayOf(android.R.attr.state_checked))
                    secondIcon?.setImageDrawable(this.current)
                }
            }

            typedArray.getString(R.styleable.ExtendEditText_separator)?.apply { separator = this }

            typedArray.recycle()
        }
    }


    private fun initClearIcon(drawable: Drawable) {
        val clear = ImageView(context)
        clear.setImageDrawable(drawable)

        clear.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val params = LayoutParams(drawable.intrinsicWidth + dip2px(10), LayoutParams.MATCH_PARENT)
        params.rules[ALIGN_PARENT_RIGHT] = TRUE
        clear.layoutParams = params
        clear.id = CLEAR_ICON_ID
        addView(clear, params)

        firstIcon = clear
    }

    private fun initPasswordIcon(drawable: Drawable) {
        val passwordIcon = ImageView(context)
        passwordIcon.setImageDrawable(drawable)
        passwordIcon.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val params = LayoutParams(drawable.intrinsicWidth + dip2px(10), LayoutParams.MATCH_PARENT)
        params.rules[LEFT_OF] = CLEAR_ICON_ID
        addView(passwordIcon, params)

        secondIcon = passwordIcon
    }


    private fun init() {

        firstIcon?.setOnClickListener {
            mEditText.setText("")
        }



        secondIcon?.setOnClickListener { _ ->
            isChecked = !isChecked
            if (isChecked) {
                stateListDrawable?.state = intArrayOf(-android.R.attr.state_checked)
                mEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                stateListDrawable?.state = intArrayOf(android.R.attr.state_checked)
                mEditText.transformationMethod = null
            }
            secondIcon?.setImageDrawable(stateListDrawable?.current)
        }

        mEditText.setOnFocusChangeListener { _, hasFocus ->
            this.hasFocus = hasFocus
            refreshRightIcons()
        }

        mEditText.addTextChangedListener(this)
    }

    fun getValue(): String {
        return mEditText.text.toString().replace(separator, "")
    }

    fun setFormatter(numberFormatter: NumberFormatter) {
        this.numberFormatter = numberFormatter
    }

    fun getRealTextView() = mEditText

    override fun afterTextChanged(s: Editable?) {
        isEmpty = s.isNullOrEmpty()
        refreshRightIcons()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (before > 0 && count == 0) {
            return
        }
        val formattedText = numberFormatter.getFormattedText(s.toString(), separator)
        if (formattedText != s.toString()) {
            mEditText.setText(formattedText)
            mEditText.setSelection(formattedText.length)
        }

    }


    private fun refreshRightIcons() {
        firstIcon?.visibility = if (!isEmpty && hasFocus) View.VISIBLE else View.GONE
        secondIcon?.visibility = if (!isEmpty && hasFocus) View.VISIBLE else View.GONE

    }

    private fun dip2px(dipValue: Int): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}
