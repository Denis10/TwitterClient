package com.vodolazskiy.twitterclient.data.db.repositories

import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.data.services.userzone.request.GetUserFeedsDataRequest
import io.reactivex.Flowable

interface UserFeedRepository : DBRepository<Long, UserFeedEntity>{

    fun getFeeds(request: GetUserFeedsDataRequest): Flowable<List<UserFeedEntity>>
}