package com.vodolazskiy.twitterclient.core.di.modules

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.data.services.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.data.services.openzone.TwitterOauthService
import com.vodolazskiy.twitterclient.data.services.openzone.TwitterOauthServiceImpl
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterService
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterServiceModule {

    @Singleton
    @Provides
    fun provideTwitterService(@DataConverterQualifier converter: ConvertersContext, handler: NetworkExceptionHandler): TwitterService =
            TwitterServiceImpl(TwitterCore.getInstance().sessionManager.activeSession, converter, handler)

    @Singleton
    @Provides
    fun provideTwitterOauthService(@DataConverterQualifier converter: ConvertersContext, handler: NetworkExceptionHandler):
            TwitterOauthService = TwitterOauthServiceImpl(converter, handler)
}