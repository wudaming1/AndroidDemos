package com.aries.demo.widget.extendEditText

import android.annotation.SuppressLint
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
    private var isPassword = true
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

        init(context)

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
                //fixme 不传入StateListDrawable，传入两张图片自己生成，另外callback被清除需要注意下。
                if (this is StateListDrawable) {
                    stateListDrawable = this
                    this.callback = object : Drawable.Callback {
                        override fun unscheduleDrawable(who: Drawable?, what: Runnable?) {
                        }

                        override fun invalidateDrawable(who: Drawable) {
                            secondIcon?.setImageDrawable(who.current)
                        }

                        override fun scheduleDrawable(who: Drawable?, what: Runnable?, `when`: Long) {
                        }

                    }
                    this.setState(intArrayOf(android.R.attr.state_checked))
                }


            }

            typedArray.recycle()
        }
    }


    @SuppressLint("ResourceType")
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


    private fun init(context: Context) {

        firstIcon?.setOnClickListener {
            mEditText.setText("")
        }



        secondIcon?.setOnClickListener { _ ->
            isChecked = !isChecked
            if (isChecked) {
                stateListDrawable?.state = intArrayOf(android.R.attr.state_checked)
                mEditText.transformationMethod = PasswordTransformationMethod.getInstance()
//                secondIcon?.setImageResource(R.drawable.edit_text_hide)
            } else {
                stateListDrawable?.state = intArrayOf(-android.R.attr.state_checked)
                mEditText.transformationMethod = null
//                secondIcon?.setImageResource(R.drawable.edit_text_show)
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
        val formattedText = numberFormatter.getFormattedText(s.toString(), " ")
        if (formattedText != s.toString()) {
            mEditText.setText(formattedText)
            mEditText.setSelection(formattedText.length)
        }

    }


    private fun refreshRightIcons() {
        firstIcon?.visibility = if (!isEmpty && hasFocus) View.VISIBLE else View.GONE
        secondIcon?.visibility = if (!isEmpty && hasFocus && isPassword) View.VISIBLE
        else View.GONE

    }

    private fun dip2px(dipValue: Int): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}
