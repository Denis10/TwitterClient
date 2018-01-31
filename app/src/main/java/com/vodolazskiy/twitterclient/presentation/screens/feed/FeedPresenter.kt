package com.vodolazskiy.twitterclient.presentation.screens.feed

import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.BasePresenter

interface FeedPresenter : BasePresenter<FeedView> {
    fun refreshFeed(firstFeedItem: UserFeed?)
}