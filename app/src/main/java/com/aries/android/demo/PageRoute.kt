package com.aries.android.demo

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.aries.base.launch

/**
 * Author wudaming
 * Created on 2018/5/22
 * 模块入口注册表，需要手动添加，修改需要同步
 */

object PageRoute {
    const val MaterialDemo = "com.aries.demo.materialdesign.MaterialActivity"
    const val NestedScrollingDemo = "com.arise.demo.nestedscrolling.activities.NestedScrollingMainActivity"
    const val RecyclerViewDemo = "com.aries.sdk.recyclerview.RecyclerViewActivity"
    const val AnimDemo = "com.aries.demo.animation.AnimActivity"
    const val AIDLDemo = "com.aries.demo.service.BookManagerActivity"

    fun launch(context: Context, path: String) {
        val clazz = findClazz<Activity>(context, path)
        if (clazz != null) {
            context.launch(clazz)
        }

    }

    private fun <T> findClazz(context: Context, path: String): Class<T>? {
        try {
            val clazz = Class.forName(path)
            return clazz as Class<T>
        } catch (e: ClassNotFoundException) {
            Log.e("route", "class not found :$path")
            Toast.makeText(context, "没有找到对应模块", Toast.LENGTH_SHORT).show()
        } catch (e: ClassCastException) {
            Toast.makeText(context, "强制转型失败！", Toast.LENGTH_SHORT).show()
            Log.e("route", "class cast fail :$path")
        }
        return null
    }
}