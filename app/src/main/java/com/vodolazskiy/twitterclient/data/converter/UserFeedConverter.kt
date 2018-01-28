package com.vodolazskiy.twitterclient.data.converter

import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.IConvertersContext
import com.vodolazskiy.twitterclient.core.converter.IConvertersContextVisitor
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeed

/**
 * Created by denis on 1/28/18.
 */
class UserFeedConverter: IConvertersContextVisitor {
    override fun visit(convertersContext: IConvertersContext) {
        convertersContext.registerConverter(Tweet::class.java, UserFeed::class.java, this::restToDb)
    }

    private fun restToDb(inItem: Tweet, token: Any?, convertersContext: IConvertersContext): UserFeed {
        //todo add fields
        return UserFeedDbEntity(inItem.idStr)
    }
}