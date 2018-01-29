package com.vodolazskiy.twitterclient.presentation.screens.login

import com.vodolazskiy.twitterclient.presentation.BaseView

interface LoginView : BaseView {

    fun openFeedScreen()

    fun showLoginError(text: String)
}