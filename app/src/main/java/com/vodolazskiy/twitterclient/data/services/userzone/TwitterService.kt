package com.vodolazskiy.twitterclient.data.services.userzone

import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.data.services.userzone.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.data.services.userzone.request.PostTweetDataRequest
import io.reactivex.Observable

interface TwitterService {
    fun getTimelineItems(request: GetUserFeedsDataRequest): Observable<List<UserFeedEntity>>

    fun sendTweet(request: PostTweetDataRequest): Observable<UserFeedEntity>
}