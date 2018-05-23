package com.aries.demo.service.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.aries.base.BaseActivity
import com.aries.demo.service.R
import kotlinx.android.synthetic.main.activity_messenger.*


private const val TAG = "MessengerActivity"

class MessengerActivity : BaseActivity() {

    private var mService: Messenger? = null

    private val mReplyMessenger = Messenger(ReplyHandler())

    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName) {

        }

        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            mService = Messenger(p1)
            val msg = Message.obtain(null, MessengerHandler.MESSAGE_GET_BOOK)
            msg.replyTo = mReplyMessenger
            try {
                mService?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        textView.setOnClickListener {
            sendMessage()
        }

    }

    private fun sendMessage() {
        val msg = Message.obtain(null, MessengerHandler.MESSAGE_GET_BOOK)
        msg.replyTo = mReplyMessenger
        try {
            mService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }
}

class ReplyHandler() : Handler() {
    override fun handleMessage(msg: Message?) {
        msg?.apply {
            when (what) {
                MessengerHandler.MESSAGE_RETURN_BOOK -> {
                    msg.data?.apply {
                        Log.e(TAG, "收到书籍：${getString("reply")}")
                    }
                }
            }
        }
    }
}
