package com.vodolazskiy.twitterclient

import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.MediaEntity
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.TweetEntities
import com.vodolazskiy.twitterclient.data.converter.DataConverter
import com.vodolazskiy.twitterclient.data.converter.composite.LoginConverter
import com.vodolazskiy.twitterclient.data.converter.composite.UserFeedEntityConverter
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.services.openzone.responses.LoginDataResponse
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import java.text.SimpleDateFormat
import java.util.*

class DataConverterSpec : Spek({

    given("Data converter") {
        val loginConverter = LoginConverter()
        val feedConverter = UserFeedEntityConverter()
        val converter = DataConverter(userFeedEntityConverter = feedConverter, loginConverter = loginConverter)
        val token = TwitterAuthToken("token", "secret")
        val twitterSession = TwitterSession(token, 123L, "userName")

        on("login response") {
            val response = converter.convert(twitterSession, LoginDataResponse::class.java)

            it("should return login data model with token and user name") {
                assertEquals("token", response.token)
                assertEquals("userName", response.userName)
            }
        }

        val media = MediaEntity("", "", "", 0, 1, 12L, "12",
                "", "https://pbs.twimg.com/media/DVFd4hXXUAErOvH.jpg", null, 0L, "", "photo", null, "")
        val tweetEntities = TweetEntities(null, null, arrayListOf(media), null, null)
        val tweet = Tweet(null, "Sat Feb 03 04:55:03 +0000 2018", null, null, tweetEntities,
                0, false, "", 1234L, "1234", "", 0L,
                "", 0L, "", "en", null,
                false, null, 0L, "",
                null, 0, false, null,
                "", "text", null, false, null, false,
                null, null, null
        )
        on("tweet") {
            val feedEntity = converter.convert(tweet, UserFeedDbEntity::class.java)
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
            it("should return user feed data model with id, date, text and media url") {
                assertEquals(1234, feedEntity.id)
                assertEquals(formatter.parse("Sat Feb 03 04:55:03 +0000 2018"), feedEntity.createdAt)
                assertEquals("text", feedEntity.text)
                assertEquals("https://pbs.twimg.com/media/DVFd4hXXUAErOvH.jpg", feedEntity.mediaUrlHttps)
            }
        }

        val mediaNoUrl = MediaEntity("", "", "", 0, 1, 12L, "12",
                "", null, null, 0L, "", "photo", null, "")
        val tweetEntitiesNoUrl = TweetEntities(null, null, arrayListOf(mediaNoUrl), null, null)
        val tweetNoUrl = Tweet(null, "Sat Feb 03 04:55:03 +0000 2018", null, null, tweetEntitiesNoUrl,
                0, false, "", 1234L, "1234", "", 0L,
                "", 0L, "", "en", null,
                false, null, 0L, "",
                null, 0, false, null,
                "", "text", null, false, null, false,
                null, null, null
        )
        on("tweet without url") {
            val feedEntity = converter.convert(tweetNoUrl, UserFeedDbEntity::class.java)
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
            it("should return user feed data model with id, date, text and with empty media url") {
                assertEquals(1234, feedEntity.id)
                assertEquals(formatter.parse("Sat Feb 03 04:55:03 +0000 2018"), feedEntity.createdAt)
                assertEquals("text", feedEntity.text)
                assertEquals(null, feedEntity.mediaUrlHttps)
            }
        }
    }
})