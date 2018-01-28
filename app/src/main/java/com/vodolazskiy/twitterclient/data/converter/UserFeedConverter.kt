package com.vodolazskiy.twitterclient.data.converter

import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.ConvertersContextVisitor
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeed

class UserFeedConverter : ConvertersContextVisitor {
    override fun visit(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(Tweet::class.java, UserFeed::class.java, this::restToDb)
    }

    private fun restToDb(inItem: Tweet, token: Any?, convertersContext: ConvertersContext): UserFeed {
        //todo add fields
        return UserFeedDbEntity(inItem.idStr)
    }
}