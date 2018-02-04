package com.vodolazskiy.twitterclient.presentation.screens.login

import android.content.Context

interface LoginActivityManager {
    fun start(context: Context)
    fun startFromLogout(context: Context)
}