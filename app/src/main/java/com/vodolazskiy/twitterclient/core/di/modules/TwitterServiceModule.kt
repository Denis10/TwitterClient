package com.vodolazskiy.twitterclient.core.di.modules

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.core.util.TopActivityProvider
import com.vodolazskiy.twitterclient.data.services.openzone.TwitterOauthServiceImpl
import com.vodolazskiy.twitterclient.data.services.userzone.TwitterServiceImpl
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterService
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
    fun provideTwitterOauthService(@DataConverterQualifier converter: ConvertersContext,
                                   handler: NetworkExceptionHandler,
                                   topActivityProvider: TopActivityProvider): TwitterOauthService =
            TwitterOauthServiceImpl(converter, handler, topActivityProvider)
}
