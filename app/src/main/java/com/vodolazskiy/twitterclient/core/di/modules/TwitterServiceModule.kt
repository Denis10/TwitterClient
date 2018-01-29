package com.vodolazskiy.twitterclient.core.di.modules

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterService
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterServiceImpl
import com.vodolazskiy.twitterclient.data.services.login.TwitterOauthService
import com.vodolazskiy.twitterclient.data.services.login.TwitterOauthServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterServiceModule {

    @Singleton
    @Provides
    fun provideTwitterService(): TwitterService =
            TwitterServiceImpl(TwitterCore.getInstance().sessionManager.activeSession)

    @Singleton
    @Provides
    fun provideTwitterOauthService(converter: ConvertersContext): TwitterOauthService =
            TwitterOauthServiceImpl(converter)
}