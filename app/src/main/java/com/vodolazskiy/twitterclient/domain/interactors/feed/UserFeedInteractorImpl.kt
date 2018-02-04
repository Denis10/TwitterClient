package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DomainConverterQualifier
import com.vodolazskiy.twitterclient.core.subscribeAsync
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterService
import com.vodolazskiy.twitterclient.data.services.userzone.request.GetUserFeedsDataRequest
import com.vodolazskiy.twitterclient.data.services.userzone.request.PostTweetDataRequest
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest
import io.reactivex.Observable
import javax.inject.Inject

class UserFeedInteractorImpl @Inject constructor(private val twitterService: TwitterService,
                                                 private val feedRepository: UserFeedRepository,
                                                 @DomainConverterQualifier private val converter: ConvertersContext) :
        UserFeedInteractor {

    override fun getFeeds(request: GetNewerUserFeedsRequest): Observable<List<UserFeed>> {
        return twitterService.getTimelineItems(converter.convert(request, GetUserFeedsDataRequest::class.java))
                .flatMap { it -> feedRepository.insertAll(it).toObservable().subscribeAsync() }
                .map(converter.convertCollection(UserFeed::class.java))
    }

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

    override fun sendTweet(request: PostTweetRequest): Observable<Unit> {
        return twitterService.sendTweet(converter.convert(request, PostTweetDataRequest::class.java))
                .flatMap { it -> feedRepository.insert(it).toObservable().subscribeAsync() }
                .map { Unit }
    }
}