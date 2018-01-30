package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import io.reactivex.Observable

interface UserFeedInteractor {

    fun getFeeds(): Observable<List<UserFeed>>
}