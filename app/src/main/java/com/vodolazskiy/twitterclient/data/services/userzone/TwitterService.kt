package com.vodolazskiy.twitterclient.data.services.userzone

import com.twitter.sdk.android.core.models.User
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import io.reactivex.Observable

interface TwitterService {
    fun getTimelineItems(): Observable<List<UserFeedEntity>>

    fun getCurrentUser(): Observable<User>

    fun sendTweet(tweetText: String): Observable<Boolean>
}