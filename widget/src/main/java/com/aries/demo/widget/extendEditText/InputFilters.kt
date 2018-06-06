package com.aries.demo.widget.extendEditText

import android.text.InputFilter
import android.text.Spanned
import android.util.Log

/**
 * Author wudaming
 * Created on 2018/6/5
 */
class PhoneInputFiler(private val separator: Char = ' ') : InputFilter {


    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
        val virtualResult = StringBuilder(dest.subSequence(0, dstart))
        virtualResult.append(source)
        virtualResult.append(dest.subSequence(dend, dest.length))
        Log.e("wdm", virtualResult.toString())
        val cutOffInput = if (source.length + dstart > 13) {
            source.subSequence(0, 13 - dstart)
        } else {
            source
        }


        val resultText = StringBuilder()
        val length = cutOffInput.length
        if (dstart == 4) {
            resultText.append(separator)
        }

        return source
    }

}