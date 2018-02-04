package com.vodolazskiy.twitterclient.presentation.screens.login

import com.vodolazskiy.twitterclient.presentation.base.BaseView

interface LoginView : BaseView {

    fun openFeedScreen()

    fun showLoginError(text: String)
}