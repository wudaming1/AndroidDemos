package com.aries.tools.pageregister

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent

/**
 * Created by wudaming on 2018/4/9.
 */
object Router {
    fun goPage(path:String,context: Context){
        val clazz = Class.forName(path)
        checkLegal(clazz)
        val intent = Intent(context, clazz)
        context.startActivity(intent)
        //todo fragment 的逻辑没有添加
    }

    private fun checkLegal(clazz:Class<*>){
        if (!clazz.isAssignableFrom(Activity::class.java)
                && !clazz.isAssignableFrom(Fragment::class.java)){
            throw RuntimeException("被注释的对象必须是Activity或者Fragment类型:${clazz.name}并不是！")
        }

    }
}