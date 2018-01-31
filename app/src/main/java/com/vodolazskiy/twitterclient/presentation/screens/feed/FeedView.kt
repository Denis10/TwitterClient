package com.vodolazskiy.twitterclient.presentation.screens.feed

import android.support.v7.widget.RecyclerView
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.BaseView

interface FeedView: BaseView {

    val emptyListCount: Int
    var isEmptyViewVisible: Boolean
    val feedRecyclerView: RecyclerView

    fun showLoadingProgress()
    fun hideLoadingProgress()

    fun addItems(items: List<UserFeed>)
    fun deleteAllItems()

    fun setScreenName(name: String)
}