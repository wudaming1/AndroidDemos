package com.aries.android.demo

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by wudaming on 2018/3/26.
 */
object DensityUtils{
    fun getDensity() = {
        BaseApplication.baseInstance.resources.displayMetrics.density
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = BaseApplication.baseInstance.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dip2px(dipValue: Float): Int {
        val scale =  BaseApplication.baseInstance.getResources().getDisplayMetrics().density
        return (dipValue * scale + 0.5f).toInt()
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale =  BaseApplication.baseInstance.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }




    fun getScreenHeight():Int{
        val windowManager = BaseApplication.baseInstance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }


    fun getScreenWidth():Int{
        val windowManager = BaseApplication.baseInstance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }



    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight():Int{
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val obj = clazz.newInstance()
            val field = clazz.getField("status_bar_height")
            val x = field.get(obj).toString().toInt()
            return BaseApplication.baseInstance.resources.getDimensionPixelSize(x)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return 0
    }
}