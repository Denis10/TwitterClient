package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProvider
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedPresenterImpl
import com.vodolazskiy.twitterclient.presentation.screens.feed.adapter.FeedHolder
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginPresenterImpl
import com.vodolazskiy.twitterclient.presentation.screens.post.PostPresenterImpl
import com.vodolazskiy.twitterclient.presentation.screens.splash.SplashPresenterImpl

interface AppGraph {
    val context: Context
    val buildConfig: BuildConfigInfoProvider

    //presenters
    fun inject(injectItem: LoginPresenterImpl)

    fun inject(injectItem: FeedPresenterImpl)
    fun inject(splashPresenterImpl: SplashPresenterImpl)
    fun inject(postPresenterImpl: PostPresenterImpl)
    fun inject(feedHolder: FeedHolder)
}
