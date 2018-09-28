package com.vodolazskiy.twitterclient.presentation.screens.feed

import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.BaseView
import io.reactivex.Observable

interface FeedView : BaseView {

    val emptyListCount: Int

    var isEmptyViewVisible: Boolean

    val scrollObservable: Observable<Int>

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun addItems(items: List<UserFeed>)

    fun deleteAllItems()

    fun setScreenName(name: String)

    fun showLoadingError(error: String)

    fun scrollToTop()

    fun logout()
}
