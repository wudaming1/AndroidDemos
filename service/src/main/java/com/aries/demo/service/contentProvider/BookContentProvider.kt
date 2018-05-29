package com.aries.demo.service.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Author wudaming
 * Created on 2018/5/23
 */
class BookContentProvider() : ContentProvider() {

    override fun onCreate(): Boolean {

        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
    }

    override fun getType(uri: Uri?): String = "*/*"

}