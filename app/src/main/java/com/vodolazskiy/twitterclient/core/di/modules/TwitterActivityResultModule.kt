package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService
import com.vodolazskiy.twitterclient.presentation.screens.login.TwitterActivityResultListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterActivityResultModule {

    //todo better use named
    @Singleton
    @Provides
    fun provideTwitterActivityResultListener(service: TwitterOauthService) =
            TwitterActivityResultListener(service)
}
