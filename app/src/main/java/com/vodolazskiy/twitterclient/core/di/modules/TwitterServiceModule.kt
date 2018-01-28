package com.vodolazskiy.twitterclient.core.di.modules

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.data.services.TwitterService
import com.vodolazskiy.twitterclient.data.services.TwitterServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by denis on 1/28/18.
 */
@Module
class TwitterServiceModule {

    @Singleton
    @Provides
    fun provideTwitterService(): TwitterService =
            TwitterServiceImpl(TwitterCore.getInstance().sessionManager.activeSession)
}