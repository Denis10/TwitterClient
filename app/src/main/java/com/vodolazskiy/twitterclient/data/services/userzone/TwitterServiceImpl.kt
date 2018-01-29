package com.vodolazskiy.twitterclient.data.services.userzone

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable

internal class TwitterServiceImpl constructor(session: TwitterSession) : TwitterApiClient(session),
        TwitterService {

    override fun getTimelineItems(): Observable<List<Tweet>> {
        return Observable.create<List<Tweet>> { subscriber ->
            val callback = object : Callback<List<Tweet>>() {
                override fun success(result: Result<List<Tweet>>) {
                    subscriber.onNext(result.data)
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(e)
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
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(e)
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
                }

                override fun failure(e: TwitterException) {
                    subscriber.onError(e)
                }
            }
            statusesService.update(tweetText, null, null, null, null, null, null, null, null).enqueue(callback)
        }
    }
}