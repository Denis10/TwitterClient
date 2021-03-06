package com.vodolazskiy.twitterclient.domain.converter.composite

import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeedItem
import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.PostTweetDataRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest

class UserFeedConverter : CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(UserFeedEntity::class.java, UserFeedItem::class.java, this::dataToDomain)
        convertersContext.registerConverter(GetOlderUserFeedsRequest::class.java, GetUserFeedsDataRequest::class.java, this::olderToDataRequest)
        convertersContext.registerConverter(GetNewerUserFeedsRequest::class.java, GetUserFeedsDataRequest::class.java, this::newerToDataRequest)
        convertersContext.registerConverter(PostTweetRequest::class.java, PostTweetDataRequest::class.java, this::postToDataRequest)
    }

    private fun dataToDomain(inItem: UserFeedEntity): UserFeedItem {
        return UserFeedItem(inItem.id, inItem.createdAt, inItem.text, inItem.mediaUrlHttps)
    }

    private fun olderToDataRequest(inItem: GetOlderUserFeedsRequest): GetUserFeedsDataRequest {
        return GetUserFeedsDataRequest(inItem.limit, null, inItem.maxId)
    }

    private fun newerToDataRequest(inItem: GetNewerUserFeedsRequest): GetUserFeedsDataRequest {
        return GetUserFeedsDataRequest(inItem.limit, inItem.sinceId, null)
    }

    private fun postToDataRequest(inItem: PostTweetRequest): PostTweetDataRequest {
        return PostTweetDataRequest(inItem.text)
    }
}
