package com.vodolazskiy.twitterclient.domain.interactors.feed

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DomainConverterQualifier
import com.vodolazskiy.twitterclient.core.subscribeAsync
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterService
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import io.reactivex.Observable
import javax.inject.Inject

class UserFeedInteractorImpl @Inject constructor(private val twitterService: TwitterService,
                                                 private val feedRepository: UserFeedRepository,
                                                 @DomainConverterQualifier private val converter: ConvertersContext) :
        UserFeedInteractor {
    override fun getFeeds(): Observable<List<UserFeed>> {
        return twitterService.getTimelineItems()
                .flatMap { it -> feedRepository.insertAll(it).toObservable().subscribeAsync() }
                .map { converter.convertCollection(it, UserFeed::class.java) }
    }
}