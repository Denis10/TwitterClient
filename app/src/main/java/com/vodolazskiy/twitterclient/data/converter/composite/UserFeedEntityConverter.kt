package com.vodolazskiy.twitterclient.data.converter.composite

import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity

class UserFeedEntityConverter : CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(Tweet::class.java, UserFeedDbEntity::class.java, this::restToDb)
    }

    private fun restToDb(inItem: Tweet): UserFeedDbEntity {
        //todo add fields
        return UserFeedDbEntity(inItem.idStr)
    }
}