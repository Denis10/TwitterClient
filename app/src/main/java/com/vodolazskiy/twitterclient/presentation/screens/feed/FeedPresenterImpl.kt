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
import com.vodolazskiy.twitterclient.presentation.base.adapter.PaginationTool
import com.vodolazskiy.twitterclient.presentation.base.bind
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

private const val LIMIT = 20

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
    private val selector = AtomicInteger(1)

    //offset:
    @Volatile
    private var lastFeedItem: UserFeed? = null //older
    @Volatile
    private var firstFeedItem: UserFeed? = null

    init {
        DI.component.inject(this)

        bus.register(OnNetworkConnected::class.java)
                .subscribe({
                    selector.incrementAndGet()
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
        feedInteractor.getFeeds(GetNewerUserFeedsRequest(LIMIT, feedItem.id))
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
    }

    private fun createPaginationTool(view: FeedView, skipProgressForFirstLoading: Boolean = false) {
        val recyclerView = view.feedRecyclerView
        val localEmptyListCount = view.emptyListCount
        val skipProgress = AtomicBoolean(skipProgressForFirstLoading)

        //2. unSubscribe previous pagination tool
        paginationSubscription?.dispose()


        //3. create new pagination tool
        paginationSubscription = PaginationTool.builder<List<UserFeed>>(recyclerView) {
            beforeLoadingListener = { _ ->
                if (!skipProgress.getAndSet(false)) {
                    onceViewAttached { it.showLoadingProgress() }
                }
            }
            limit = LIMIT
            emptyListCount = localEmptyListCount
            pagingListener = { offset -> getFeeds(offset) }
            keySelector = { selector.get() }
        }
                .ioToMain()
                .subscribe({ feeds ->
                    onceViewAttached {
                        if (feeds.isNotEmpty()) {
                            selector.incrementAndGet()
                        }
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
        return feedInteractor.getFeeds(GetOlderUserFeedsRequest(LIMIT, maxId))
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
