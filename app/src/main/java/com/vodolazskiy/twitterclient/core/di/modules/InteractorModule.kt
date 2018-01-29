package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.domain.interactors.feed.UserFeedInteractor
import com.vodolazskiy.twitterclient.domain.interactors.feed.UserFeedInteractorImpl
import com.vodolazskiy.twitterclient.domain.interactors.login.LoginInteractor
import com.vodolazskiy.twitterclient.domain.interactors.login.LoginInteractorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface InteractorModule {

    @Singleton
    @Binds
    fun provideLogin(login: LoginInteractorImpl): LoginInteractor

    @Singleton
    @Binds
    fun provideFeed(feed: UserFeedInteractorImpl): UserFeedInteractor
}