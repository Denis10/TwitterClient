package com.vodolazskiy.twitterclient.presentation.screens.login

import android.os.Bundle
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.BaseActivity

class LoginActivity : BaseActivity<LoginView, LoginPresenter>(), LoginView {

    override fun createPresenter(): LoginPresenter = LoginPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
    }
}