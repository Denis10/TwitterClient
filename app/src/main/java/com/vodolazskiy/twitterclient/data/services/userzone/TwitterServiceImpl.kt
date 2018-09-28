package com.vodolazskiy.twitterclient.data.services.userzone

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterService
import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.PostTweetDataRequest
import io.reactivex.Observable

internal class TwitterServiceImpl constructor(session: TwitterSession, @DataConverterQualifier private val converter: ConvertersContext,
                                              private val handler: NetworkExceptionHandler) :
        TwitterApiClient(session),
        TwitterService {

    override fun getTimelineItems(request: GetUserFeedsDataRequest): Observable<List<UserFeedEntity>> {
        return Observable.create<List<UserFeedEntity>> { subscriber ->
            val callback = object : Callback<List<Tweet>>() {
                override fun success(result: Result<List<Tweet>>) {
                    subscriber.onNext(converter.convertCollection(result.data, UserFeedEntity::class.java))
                    subscriber.onComplete()
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(handler.handle(e))
                }
            }

            statusesService.homeTimeline(request.limit, request.sinceId, request.maxId, null, true, null, null).enqueue(callback)
        }
    }

    override fun sendTweet(request: PostTweetDataRequest): Observable<UserFeedEntity> {
        return Observable.create { subscriber ->
            val callback = object : Callback<Tweet>() {
                override fun success(result: Result<Tweet>) {
                    subscriber.onNext(converter.convert(result.data, UserFeedEntity::class.java))
                    subscriber.onComplete()
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(handler.handle(e))
                }
            }
            statusesService.update(request.text, null, null, null, null, null, null, null, null).enqueue(callback)
        }
    }
}
