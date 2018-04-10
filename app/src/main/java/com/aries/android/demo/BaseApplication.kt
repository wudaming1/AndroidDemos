package com.aries.android.demo

import android.app.Application

/**
 * Created by wudaming on 2018/3/26.
 */

class BaseApplication : Application(){

    companion object {
        lateinit var baseInstance:BaseApplication
    }


    override fun onCreate() {
        baseInstance = this
        super.onCreate()
    }
}
