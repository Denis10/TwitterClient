package com.vodolazskiy.twitterclient.presentation.screens.post

import com.vodolazskiy.twitterclient.presentation.base.BaseView

interface PostView : BaseView {
    fun closeScreen()
    fun showError(error: String)

    fun showProgress()
    fun hideProgress()
}