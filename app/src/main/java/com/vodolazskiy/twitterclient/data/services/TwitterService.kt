package com.vodolazskiy.twitterclient.data.services

import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import io.reactivex.Observable

interface TwitterService {
    fun getTimelineItems(): Observable<List<Tweet>>

    fun getCurrentUser(): Observable<User>

    fun sendTweet(tweetText: String): Observable<Boolean>
}