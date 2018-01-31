package com.vodolazskiy.twitterclient.data.services.userzone

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.data.services.NetworkExceptionHandler
import io.reactivex.Observable

internal class TwitterServiceImpl constructor(session: TwitterSession, @DataConverterQualifier private val converter: ConvertersContext,
                                              private val handler: NetworkExceptionHandler) :
        TwitterApiClient(session),
        TwitterService {

    override fun getTimelineItems(): Observable<List<UserFeedEntity>> {
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

            statusesService.homeTimeline(null, null, null, null, null, null, null).enqueue(callback)
        }
    }

    override fun getCurrentUser(): Observable<User> {
        return Observable.create { subscriber ->
            val callback = object : Callback<User>() {
                override fun success(result: Result<User>) {
                    subscriber.onNext(result.data)
                    subscriber.onComplete()
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(handler.handle(e))
                }
            }

            getService(UserService::class.java).show(
                    TwitterCore.getInstance().sessionManager.activeSession.userId).enqueue(callback)
        }
    }

    override fun sendTweet(tweetText: String): Observable<Boolean> {
        return Observable.create { subscriber ->
            val callback = object : Callback<Tweet>() {
                override fun success(result: Result<Tweet>) {
                    subscriber.onNext(true)
                    subscriber.onComplete()
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(handler.handle(e))
                }
            }
            statusesService.update(tweetText, null, null, null, null, null, null, null, null).enqueue(callback)
        }
    }
}