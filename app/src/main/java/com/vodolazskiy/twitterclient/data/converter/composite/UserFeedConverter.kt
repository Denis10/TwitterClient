package com.vodolazskiy.twitterclient.data.converter.composite

import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.data.converter.registerConverter
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeed

class UserFeedConverter: CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(Tweet::class.java, UserFeed::class.java, this::restToDb)
    }

    private fun restToDb(inItem: Tweet): UserFeed {
        //todo add fields
        return UserFeedDbEntity(inItem.idStr)
    }
}