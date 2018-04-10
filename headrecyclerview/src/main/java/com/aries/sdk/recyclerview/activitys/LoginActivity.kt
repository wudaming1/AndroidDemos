package com.aries.sdk.recyclerview.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aries.sdk.recyclerview.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener { HeadDemoActivity.launch(this@LoginActivity) }

    }
}
