package com.vodolazskiy.twitterclient.presentation.screens.feed

import android.os.Bundle
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.base.BaseActivity

class FeedActivity: BaseActivity<FeedView, FeedPresenter>(), FeedView {

    override fun createPresenter(): FeedPresenter = FeedPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feed)
    }
}