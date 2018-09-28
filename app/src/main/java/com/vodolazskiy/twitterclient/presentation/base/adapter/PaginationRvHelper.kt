package com.vodolazskiy.twitterclient.presentation.base.adapter

import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

// start loading when user scrolled 1/2 items
private const val DEFAULT_START_PAGINATION_COEFFICIENT = 0.7f
// for first start of items loading then on RecyclerView there are not items and no scrolling
private const val DEFAULT_EMPTY_LIST_ITEMS_COUNT = 0
// default limit for requests
private const val DEFAULT_LIMIT = 50

/**
 * A tool to listen RecyclerView scroll events
 *
 * @param recyclerView - RecyclerView to work with
 * @param topPagination - paginate on scroll to top
 * @param limit - You can specify the limit of data loading. Must be > 0
 * @param itemsCountProvider - Optional. Implement this callback if you want to set custom items count provider
 * @param startPaginationCoefficient - This coefficient determines when PaginationTool must start loading of new items
 * 0.1 - start loading at the start of scrolling
 * 0.9 - start loading at the end of scrolling
 * Must be < 1 and > 0
 * @param emptyListCountPlusToOffset - Set true, if you want to calculate offset as offset' = offset + emptyListCount
 * @param emptyListCount - If your recyclerViewAdapter has headers/footers or other items, which not included to the
 * real dataSource, you need to set this items count here
 * Must be >= 0
 * */
class PaginationRvHelper(private val recyclerView: RecyclerView,
                         private val topPagination: Boolean = false,
                         private val limit: Int = DEFAULT_LIMIT,
                         private val itemsCountProvider: (() -> Int) = { recyclerView.adapter.itemCount },
                         private val startPaginationCoefficient: Float = DEFAULT_START_PAGINATION_COEFFICIENT,
                         private val emptyListCountPlusToOffset: Boolean = false,
                         private val emptyListCount: Int = DEFAULT_EMPTY_LIST_ITEMS_COUNT
) {

    fun getScrollObservable(): Observable<Int> {
        val listener = { emitter: ObservableEmitter<Int> ->
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemsCount = itemsCountProvider.invoke()
                    when {
                        topPagination -> {
                            val position = recyclerView.getFirstVisibleItemPosition()
                            val itemsOnScreen = recyclerView.getLastVisibleItemPosition() - position
                            val updatePosition = (itemsOnScreen * startPaginationCoefficient).toInt()
                            if (position <= updatePosition) {
                                var offset = itemsCount
                                offset -= if (emptyListCountPlusToOffset) 0 else emptyListCount
                                emitter.onNext(offset)
                            }
                        }
                        else -> {
                            val position = recyclerView.getLastVisibleItemPosition()
                            val updatePosition = (itemsCount.toFloat() - 1f - limit.toFloat() * startPaginationCoefficient).toInt()
                            if (position >= updatePosition) {
                                var offset = itemsCount
                                offset -= if (emptyListCountPlusToOffset) 0 else emptyListCount
                                emitter.onNext(offset)
                            }
                        }
                    }
                }
            }
        }

        return Observable.create { emitter ->
            recyclerView.post {
                val localListener = listener(emitter)
                recyclerView.addOnScrollListener(localListener)
                emitter.setCancellable {
                    recyclerView.removeOnScrollListener(localListener)
                }

                val itemsCount = itemsCountProvider.invoke()
                if (itemsCount == emptyListCount) {
                    var offset = itemsCount
                    offset -= if (emptyListCountPlusToOffset) 0 else emptyListCount
                    emitter.onNext(offset)
                }
            }
        }
    }
}
