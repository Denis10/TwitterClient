package com.vodolazskiy.twitterclient.presentation.screens.feed

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeedItem
import com.vodolazskiy.twitterclient.presentation.base.BaseActivity
import com.vodolazskiy.twitterclient.presentation.screens.feed.adapter.FeedAdapter
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.*
import javax.inject.Inject

class FeedActivity : BaseActivity<FeedView, FeedPresenter>(), FeedView {

    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var adapter: FeedAdapter

    override val emptyListCount: Int get() = adapter.emptyItemCount
    override var isEmptyViewVisible: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                emptyView.visibility = if (value) View.VISIBLE else View.GONE
            }
        }
    override val feedRecyclerView: RecyclerView get() = rvFeeds
    override fun createPresenter(): FeedPresenter = FeedPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feed)
        swipeRefreshLayout.setOnRefreshListener {
            val item: UserFeed? = if (adapter.dataStorage.size == 0) null else adapter.dataStorage.get(0)
            presenter.refreshFeed(item)
        }
        rvFeeds.adapter = adapter
    }

    override fun showLoadingProgress() = run { adapter.isLoadingEnabled = true }
    override fun hideLoadingProgress() {
        adapter.isLoadingEnabled = false
        swipeRefreshLayout.post {
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun addItems(items: List<UserFeed>) = adapter.add(items)
    override fun deleteAllItems() = adapter.clear()

    override fun setScreenName(name: String) {
        title = name
    }

    override fun showLoadingError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_FEED_ADAPTER, ArrayList(adapter.dataStorage.toList()))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            adapter.set(savedInstanceState.getParcelableArrayList<Parcelable>(STATE_FEED_ADAPTER) as List<UserFeed>)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        private const val STATE_FEED_ADAPTER = "STATE_FEED_ADAPTER"
    }
}