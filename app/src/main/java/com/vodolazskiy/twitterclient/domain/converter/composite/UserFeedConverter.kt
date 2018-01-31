package com.vodolazskiy.twitterclient.domain.converter.composite

import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.data.services.userzone.request.GetUserFeeds
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeedItem
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeeds
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeeds

class UserFeedConverter : CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(UserFeedEntity::class.java, UserFeedItem::class.java, this::dataToDomain)
        convertersContext.registerConverter(GetOlderUserFeeds::class.java, GetUserFeeds::class.java, this::olderToDataRequest)
        convertersContext.registerConverter(GetNewerUserFeeds::class.java, GetUserFeeds::class.java, this::newerToDataRequest)
    }

    private fun dataToDomain(inItem: UserFeedEntity): UserFeedItem {
        //todo add fields
        return UserFeedItem(inItem.id, inItem.createdAt, inItem.text)
    }

    private fun olderToDataRequest(inItem: GetOlderUserFeeds): GetUserFeeds {
        return GetUserFeeds(inItem.limit, null,  inItem.maxId)
    }

    private fun newerToDataRequest(inItem: GetNewerUserFeeds): GetUserFeeds {
        return GetUserFeeds(inItem.limit, inItem.sinceId,  null)
    }
}