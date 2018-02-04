package com.vodolazskiy.twitterclient.presentation.screens.login

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class LoginActivityManagerImpl @Inject constructor() : LoginActivityManager {
    override fun start(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun startFromLogout(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
}