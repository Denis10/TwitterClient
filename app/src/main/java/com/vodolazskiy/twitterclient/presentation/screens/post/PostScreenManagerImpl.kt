package com.vodolazskiy.twitterclient.presentation.screens.post

import android.support.v4.app.FragmentManager
import javax.inject.Inject

class PostScreenManagerImpl @Inject constructor() : PostScreenManager {
    override fun start(fragmentManager: FragmentManager) {
        PostDialog.newInstance().show(fragmentManager, PostDialog::class.java.name)
    }
}