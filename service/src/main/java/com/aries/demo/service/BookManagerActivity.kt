package com.aries.demo.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.aries.base.BaseActivity
import kotlinx.android.synthetic.main.activity_book_manager.*

private const val TAG = "BookManagerActivity"

class BookManagerActivity : BaseActivity() {


    private val bookList = ArrayList<Book>()
    private var isBinded = false

    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName) {

        }

        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            val bookManager = IBookManager.Stub.asInterface(p1)
            try {
                bookList.clear()
                bookList.addAll(bookManager.bookList)
                Log.e(TAG, bookList.toString())
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_manager)
        openService.setOnClickListener {
            Log.e(TAG, "start")
            bindService()
        }

    }

    private fun bindService() {
        isBinded = true
        val intent = Intent(this, MyService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (isBinded)
            unbindService(mConnection)
        super.onDestroy()
    }
}
