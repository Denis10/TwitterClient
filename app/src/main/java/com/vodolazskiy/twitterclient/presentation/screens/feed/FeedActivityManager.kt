package com.vodolazskiy.twitterclient.presentation.screens.feed

import android.content.Context
import android.content.Intent
import javax.inject.Inject

interface FeedActivityManager {

    fun start(context: Context)
}

class FeedActivityManagerImpl @Inject constructor() : FeedActivityManager {

    override fun start(context: Context) {
        context.startActivity(Intent(context, FeedActivity::class.java))
    }
}
