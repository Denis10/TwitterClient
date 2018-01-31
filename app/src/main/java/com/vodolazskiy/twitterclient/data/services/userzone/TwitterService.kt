package com.vodolazskiy.twitterclient.data.services.userzone

import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import io.reactivex.Observable

interface TwitterService {
    fun getTimelineItems(): Observable<List<UserFeedEntity>>

    fun sendTweet(tweetText: String): Observable<Boolean>
}