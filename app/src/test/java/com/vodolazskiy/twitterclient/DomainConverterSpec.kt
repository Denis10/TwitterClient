package com.vodolazskiy.twitterclient

import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.PostTweetDataRequest
import com.vodolazskiy.twitterclient.domain.converter.DomainConverter
import com.vodolazskiy.twitterclient.domain.converter.composite.UserFeedConverter
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeedItem
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import java.util.*

class DomainConverterSpec : Spek({
    given("Domain converter") {
        val feedConverter = UserFeedConverter()
        val converter = DomainConverter(feedConverter)

        val date = Date()
        val feedEntity = UserFeedDbEntity(1L, date, "text", "media_url")

        on("user feed entity") {
            val feedItem = converter.convert(feedEntity, UserFeedItem::class.java)

            it("should return user feed data item with id, date, text and media url") {
                Assert.assertEquals(1L, feedItem.id)
                Assert.assertEquals(date, feedItem.createdAt)
                Assert.assertEquals("text", feedItem.text)
                Assert.assertEquals("media_url", feedItem.mediaUrlHttps)
            }
        }

        val feedEntityNoUrl = UserFeedDbEntity(1L, date, "text", null)
        on("user feed entity without url") {
            val feedItem = converter.convert(feedEntityNoUrl, UserFeedItem::class.java)

            it("should return user feed data item with id, date, text and without media url") {
                Assert.assertEquals(1L, feedEntityNoUrl.id)
                Assert.assertEquals(date, feedEntityNoUrl.createdAt)
                Assert.assertEquals("text", feedEntityNoUrl.text)
                Assert.assertEquals(null, feedEntityNoUrl.mediaUrlHttps)
            }
        }

        val getOlderUserFeedsRequest = GetOlderUserFeedsRequest(10, 100L)
        on("GetOlderUserFeedsRequest") {
            val getUserFeedsDataRequest = converter.convert(getOlderUserFeedsRequest, GetUserFeedsDataRequest::class.java)

            it("should return GetUserFeedsDataRequest") {
                Assert.assertEquals(10, getUserFeedsDataRequest.limit)
                Assert.assertEquals(100L, getUserFeedsDataRequest.maxId)
                Assert.assertEquals(null, getUserFeedsDataRequest.sinceId)
            }
        }

        val getOlderUserFeedsRequestNoMaxId = GetOlderUserFeedsRequest(10, null)
        on("GetOlderUserFeedsRequest with no maxId") {
            val getUserFeedsDataRequestNoMaxId =
                    converter.convert(getOlderUserFeedsRequestNoMaxId, GetUserFeedsDataRequest::class.java)

            it("should return GetUserFeedsDataRequest with limit only") {
                Assert.assertEquals(10, getUserFeedsDataRequestNoMaxId.limit)
                Assert.assertEquals(null, getUserFeedsDataRequestNoMaxId.maxId)
                Assert.assertEquals(null, getUserFeedsDataRequestNoMaxId.sinceId)
            }
        }

        val getNewerUserFeedsRequest = GetNewerUserFeedsRequest(10, 100L)
        on("GetNewerUserFeedsRequest") {
            val getUserFeedsDataRequest = converter.convert(getNewerUserFeedsRequest, GetUserFeedsDataRequest::class.java)

            it("should return GetUserFeedsDataRequest") {
                Assert.assertEquals(10, getUserFeedsDataRequest.limit)
                Assert.assertEquals(null, getUserFeedsDataRequest.maxId)
                Assert.assertEquals(100L, getUserFeedsDataRequest.sinceId)
            }
        }

        val postTweetRequest = PostTweetRequest("text")
        on("PostTweetRequest") {
            val postTweetDataRequest =
                    converter.convert(postTweetRequest, PostTweetDataRequest::class.java)

            it("should return PostTweetDataRequest") {
                Assert.assertEquals("text", postTweetDataRequest.text)
            }
        }
    }
})