package com.vodolazskiy.twitterclient.presentation.screens.post

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class PostActivityManagerImpl @Inject constructor() : PostActivityManager {
    override fun start(context: Context) {
        context.startActivity(Intent(context, PostActivity::class.java))
    }
}