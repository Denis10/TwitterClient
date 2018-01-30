package com.vodolazskiy.twitterclient.presentation.screens.feed

import com.vodolazskiy.twitterclient.domain.interactors.feed.UserFeedInteractor
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import javax.inject.Inject

class FeedPresenterImpl : BasePresenterImpl<FeedView>(), FeedPresenter {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var feedInteractor: UserFeedInteractor



}