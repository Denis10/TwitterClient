package com.vodolazskiy.twitterclient.data.converter.composite

import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import java.text.SimpleDateFormat
import java.util.*

class UserFeedEntityConverter : CompositeConverter {

    private val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)

    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(Tweet::class.java, UserFeedDbEntity::class.java, this::restToDb)
    }

    private fun restToDb(inItem: Tweet): UserFeedDbEntity {
        val date = dateFormat.parse(inItem.createdAt)
        var mediaUrl: String? = null
        inItem.extendedEntities?.media?.let {
            if (it.isNotEmpty()) {
                mediaUrl = it.filter { !it.mediaUrlHttps.isNullOrEmpty() }.map { it.mediaUrlHttps!! }.firstOrNull()
            }
        }

        return UserFeedDbEntity(inItem.id, date, inItem.text, mediaUrl)
    }
}