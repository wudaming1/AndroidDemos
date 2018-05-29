package com.aries.demo.service

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Author wudaming
 * Created on 2018/5/25
 */
class DbOpenHelper : SQLiteOpenHelper() {
    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}