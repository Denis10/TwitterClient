package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.PostTweetDataRequest
import io.reactivex.Observable

interface TwitterService {

    fun getTimelineItems(request: GetUserFeedsDataRequest): Observable<List<UserFeedEntity>>

    fun sendTweet(request: PostTweetDataRequest): Observable<UserFeedEntity>
}
