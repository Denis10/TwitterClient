package com.vodolazskiy.twitterclient.presentation.screens.post

import com.vodolazskiy.twitterclient.presentation.base.BasePresenter

interface PostPresenter : BasePresenter<PostView> {

    fun postTweet(text: String)
}