package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeeds
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeeds
import io.reactivex.Observable

interface UserFeedInteractor {

    fun getFeeds(request: GetNewerUserFeeds): Observable<List<UserFeed>>

    fun getFeeds(request: GetOlderUserFeeds): Observable<List<UserFeed>>
}