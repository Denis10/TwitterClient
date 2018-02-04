package com.vodolazskiy.twitterclient.presentation.screens.splash

import android.os.Bundle
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.base.BaseActivity
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedActivityManager
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginActivityManager
import javax.inject.Inject


class SplashActivity : BaseActivity<SplashView, SplashPresenter>(), SplashView {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var loginActivityManager: LoginActivityManager
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var feedActivityManager: FeedActivityManager

    override fun createPresenter(): SplashPresenter = SplashPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun openLoginScreen() {
        loginActivityManager.start(this)
        finish()
    }

    override fun openFeedScreen() {
        feedActivityManager.start(this)
        finish()
    }
}