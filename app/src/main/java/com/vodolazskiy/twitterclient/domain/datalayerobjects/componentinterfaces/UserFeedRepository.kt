package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import io.reactivex.Flowable

interface UserFeedRepository : DBRepository<Long, UserFeedEntity> {

    fun getFeeds(request: GetUserFeedsDataRequest): Flowable<List<UserFeedEntity>>
}