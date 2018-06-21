package com.aries.android.demo.compress

import android.databinding.ObservableField
import android.graphics.BitmapFactory

/**
 * Author wudaming
 * Created on 2018/6/20
 */
class CompressViewModel {

    private val compressOption = BitmapFactory.Options()

    val width = ObservableField<String>()
    val height = ObservableField<String>()


    fun getOption(): BitmapFactory.Options {

        compressOption.outWidth = width.get()?.toInt() ?: -1
        compressOption.outHeight = height.get()?.toInt() ?: -1
        return compressOption
    }

}
