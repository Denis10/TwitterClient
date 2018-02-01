package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest
import io.reactivex.Observable

interface UserFeedInteractor {

    fun getFeeds(request: GetNewerUserFeedsRequest): Observable<List<UserFeed>>

    fun getFeeds(request: GetOlderUserFeedsRequest): Observable<List<UserFeed>>

    fun sendTweet(request: PostTweetRequest): Observable<Unit>
}