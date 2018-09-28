package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DomainConverterQualifier
import com.vodolazskiy.twitterclient.core.subscribeAsync
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterService
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.UserFeedRepository
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.PostTweetDataRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest
import io.reactivex.Observable
import javax.inject.Inject

interface UserFeedInteractor {

    fun getFeeds(request: GetNewerUserFeedsRequest): Observable<List<UserFeed>>

    fun getFeeds(request: GetOlderUserFeedsRequest): Observable<List<UserFeed>>

    fun sendTweet(request: PostTweetRequest): Observable<Unit>
}

class UserFeedInteractorImpl @Inject constructor(private val twitterService: TwitterService,
                                                 private val feedRepository: UserFeedRepository,
                                                 @DomainConverterQualifier private val converter: ConvertersContext) :
        UserFeedInteractor {

    override fun getFeeds(request: GetNewerUserFeedsRequest): Observable<List<UserFeed>> =
            twitterService.getTimelineItems(converter.convert(request, GetUserFeedsDataRequest::class.java))
                    .flatMap { it -> feedRepository.insertAll(it).toObservable().subscribeAsync() }
                    .map(converter.convertCollection(UserFeed::class.java))

    override fun getFeeds(request: GetOlderUserFeedsRequest): Observable<List<UserFeed>> {
        val dataRequest = converter.convert(request, GetUserFeedsDataRequest::class.java)
        return twitterService.getTimelineItems(dataRequest)
                .flatMap { it -> feedRepository.insertAll(it).toObservable().subscribeAsync() }
                .onErrorResumeNext { it: Throwable ->
                    L.exception(it)
                    feedRepository.getFeeds(dataRequest).toObservable().subscribeAsync()
                }
                .map(converter.convertCollection(UserFeed::class.java))
    }

    override fun sendTweet(request: PostTweetRequest): Observable<Unit> =
            twitterService.sendTweet(converter.convert(request, PostTweetDataRequest::class.java))
                    .flatMap { it -> feedRepository.insert(it).toObservable().subscribeAsync() }
                    .map { Unit }
}
