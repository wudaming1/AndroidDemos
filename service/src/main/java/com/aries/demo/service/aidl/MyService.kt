package com.aries.demo.service.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.aries.demo.service.Book
import com.aries.demo.service.IBookManager
import java.util.concurrent.CopyOnWriteArrayList


private const val TAG = "MyService"

class MyService : Service() {

    private val books = CopyOnWriteArrayList<Book>()

    private val mIBinder = object : IBookManager.Stub() {

        override fun getBookList(): List<Book> {
            return books
        }

        override fun addBook(book: Book) {
            books.add(book)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate")
        books.add(Book(1, "Android"))
        books.add(Book(2, "Java"))
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "onBind")
        return mIBinder
    }
}
