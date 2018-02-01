package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedActivityManager
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedActivityManagerImpl
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginActivityManager
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginActivityManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ActivityProviderModule {

    @Singleton
    @Binds
    fun provideFeedActivityManager(manager: FeedActivityManagerImpl): FeedActivityManager

    @Singleton
    @Binds
    fun provideLoginActivityManager(manager: LoginActivityManagerImpl): LoginActivityManager

}