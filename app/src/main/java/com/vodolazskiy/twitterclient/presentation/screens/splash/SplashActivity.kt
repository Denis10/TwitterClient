package com.vodolazskiy.twitterclient.presentation.screens.splash

import android.os.Bundle
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.BaseActivity

class SplashActivity : BaseActivity<SplashView, SplashPresenter>(), SplashView {

    override fun createPresenter(): SplashPresenter = SplashPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}