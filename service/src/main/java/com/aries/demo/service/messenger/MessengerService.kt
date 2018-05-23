package com.aries.demo.service.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class MessengerService : Service() {

    companion object {
        const val TAG = "MessengerService"
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent): IBinder {
        return mMessenger.binder
    }

}

class MessengerHandler : Handler() {
    companion object {
        const val MESSAGE_GET_BOOK = 1
        const val MESSAGE_RETURN_BOOK = 2

    }

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            MESSAGE_GET_BOOK -> {
                Log.e(MessengerService.TAG, "收到get book的请求！")
                msg.replyTo?.apply {
                    val bundle = Bundle()
                    bundle.putString("reply", "books!")
                    val replyMessage = Message.obtain(null, MESSAGE_RETURN_BOOK)
                    replyMessage.data = bundle
                    send(replyMessage)
                }
            }
        }
    }
}
