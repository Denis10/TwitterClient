package com.vodolazskiy.twitterclient.data.services.userzone

import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.data.services.userzone.request.GetUserFeeds
import io.reactivex.Observable

interface TwitterService {
    fun getTimelineItems(request: GetUserFeeds): Observable<List<UserFeedEntity>>

    fun sendTweet(tweetText: String): Observable<Boolean>
}