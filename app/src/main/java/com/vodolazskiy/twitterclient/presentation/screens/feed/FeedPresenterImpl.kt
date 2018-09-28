package com.vodolazskiy.twitterclient.presentation.screens.feed

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.eventbus.EventBusProvider
import com.vodolazskiy.twitterclient.core.eventbus.events.OnNetworkConnected
import com.vodolazskiy.twitterclient.core.ioToMain
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.interactors.feed.UserFeedInteractor
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetNewerUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.GetOlderUserFeedsRequest
import com.vodolazskiy.twitterclient.domain.interactors.login.OpenZoneInteractor
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import com.vodolazskiy.twitterclient.presentation.base.bind
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

const val PAGINATION_LIMIT = 20

class FeedPresenterImpl : BasePresenterImpl<FeedView>(), FeedPresenter {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var feedInteractor: UserFeedInteractor
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var openZoneInteractor: OpenZoneInteractor
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var bus: EventBusProvider

    private var paginationSubscription: Disposable? = null

    //offset:
    @Volatile
    private var lastFeedItem: UserFeed? = null //older
    @Volatile
    private var firstFeedItem: UserFeed? = null
    private val hasMore = AtomicBoolean(true)

    init {
        DI.component.inject(this)

        bus.register(OnNetworkConnected::class.java)
                .subscribe({
                    hasMore.set(true) // unlock pagination
                    firstFeedItem?.let {
                        addItemsToTop(feedItem = it, scrollToTop = false)
                    }
                }, { L.exception(it) })
                .bind(this)
    }

    override fun viewStarted(view: FeedView) {
        super.viewStarted(view)

        openZoneInteractor.getUserName()
                .ioToMain()
                .subscribe({ name ->
                    onceViewAttached { it.setScreenName(name) }
                }, { L.exception(it) })
                .bind(this)

        createPaginationTool(view)
    }

    override fun viewStopped() {
        super.viewStopped()

        paginationSubscription?.dispose()
    }

    override fun refreshFeed(firstItem: UserFeed?) {
        firstFeedItem = firstItem
        onceViewAttached {
            if (firstItem == null) {
                it.deleteAllItems()
                resetOffset()
                createPaginationTool(view = it, skipProgressForFirstLoading = true)
            } else {
                addItemsToTop(feedItem = firstItem, scrollToTop = true)
            }
        }
    }

    private fun addItemsToTop(feedItem: UserFeed, scrollToTop: Boolean) {
        feedInteractor.getFeeds(GetNewerUserFeedsRequest(PAGINATION_LIMIT, feedItem.id))
                .ioToMain()
                .subscribe({ feeds ->
                    onceViewAttached {
                        updateOffset(feeds)
                        it.addItems(feeds)
                        it.hideLoadingProgress()
                        it.isEmptyViewVisible = !hasLoadedItems()
                        if (scrollToTop) {
                            it.scrollToTop()
                        }
                    }
                }, { throwable ->
                    L.exception(throwable)
                    onceViewAttached {
                        it.hideLoadingProgress()
                        it.isEmptyViewVisible = !hasLoadedItems()
                        it.showLoadingError(throwable.localizedMessage)
                    }
                })
                .bind(this)
    }

    private fun resetOffset() {
        lastFeedItem = null
        hasMore.set(true)
    }

    private fun createPaginationTool(view: FeedView, skipProgressForFirstLoading: Boolean = false) {
        val skipProgress = AtomicBoolean(skipProgressForFirstLoading)
        paginationSubscription?.dispose()

        paginationSubscription = view.scrollObservable
                .filter { hasMore.get() }
                .doOnNext {
                    if (!skipProgress.getAndSet(false)) {
                        onceViewAttached { it.showLoadingProgress() }
                    }
                }
                .switchMap { getFeeds(it) }
                .ioToMain()
                .subscribe({ feeds ->
                    onceViewAttached {
                        hasMore.set(feeds.isNotEmpty())
                        updateOffset(feeds)
                        it.addItems(feeds)
                        it.hideLoadingProgress()
                        it.isEmptyViewVisible = !hasLoadedItems()
                    }
                }, { throwable ->
                    L.exception(throwable)
                    onceViewAttached {
                        it.hideLoadingProgress()
                        it.isEmptyViewVisible = !hasLoadedItems()
                        it.showLoadingError(throwable.localizedMessage)
                    }
                }).bind(this)
    }

    private fun updateOffset(loadedFeedItems: List<UserFeed>) {
        if (loadedFeedItems.isEmpty()) return
        val localFeedItems = loadedFeedItems
                .sortedWith(kotlin.Comparator { o1, o2 -> o2.createdAt.compareTo(o1.createdAt) })

        if (firstFeedItem == null) {
            firstFeedItem = localFeedItems.first()
        }
        if (lastFeedItem == null) {
            lastFeedItem = localFeedItems.last()
        } else {
            val possibleLastItem = loadedFeedItems.last()
            if (possibleLastItem.createdAt.time < lastFeedItem!!.createdAt.time) {
                lastFeedItem = possibleLastItem
            }
        }
    }

    private fun hasLoadedItems(): Boolean = lastFeedItem != null

    private fun getFeeds(offset: Int): Observable<List<UserFeed>> {
        val maxId: Long? = if (offset == 0) null
        else lastFeedItem?.id
        return feedInteractor.getFeeds(GetOlderUserFeedsRequest(PAGINATION_LIMIT, maxId))
    }

    override fun logout() {
        openZoneInteractor.logout()
                .ioToMain()
                .subscribe({
                    onceViewAttached { it.logout() }
                }, {
                    L.exception(it)
                })
                .bind(this)
    }
}
