package com.vodolazskiy.twitterclient.domain.converter.composite

import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeedItem

class UserFeedConverter : CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(UserFeedEntity::class.java, UserFeedItem::class.java, this::dataToDomain)
    }

    private fun dataToDomain(inItem: UserFeedEntity): UserFeedItem {
        //todo add fields
        return UserFeedItem(inItem.id, inItem.createdAt, inItem.text)
    }
}