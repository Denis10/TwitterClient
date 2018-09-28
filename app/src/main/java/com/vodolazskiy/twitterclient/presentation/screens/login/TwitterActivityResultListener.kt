package com.vodolazskiy.twitterclient.presentation.screens.login

import com.vodolazskiy.twitterclient.core.util.ActivityResultListener
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService

class TwitterActivityResultListener(private val gateway: TwitterOauthService) : ActivityResultListener {

    override fun onActivityResult(result: ActivityResultListener.ActivityResult) = gateway.onActivityResult(result)
}
