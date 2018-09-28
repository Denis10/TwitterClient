package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.util.TopActivityProvider
import com.vodolazskiy.twitterclient.core.util.TopActivityProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ActivityUtils {

    @Singleton
    @Binds
    fun provideTopActivity(provider: TopActivityProviderImpl): TopActivityProvider
}
