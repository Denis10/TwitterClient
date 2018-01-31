package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProvider
import com.vodolazskiy.twitterclient.presentation.base.PaginationTool
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedPresenterImpl
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginPresenterImpl

interface AppGraph {
    val context: Context
    val buildConfig: BuildConfigInfoProvider

    //presenters
    fun inject(injectItem: LoginPresenterImpl)
    fun inject(injectItem: FeedPresenterImpl)
    fun inject(injectionWrapper: PaginationTool.InjectionWrapper)
}
