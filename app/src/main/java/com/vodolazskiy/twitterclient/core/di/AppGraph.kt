package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProvider
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginPresenterImpl

interface AppGraph {
    val context: Context
    val buildConfig: BuildConfigInfoProvider

    //presenters
    fun inject(injectItem: LoginPresenterImpl)
}