package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.eventbus.EventBusProvider
import com.vodolazskiy.twitterclient.core.eventbus.EventBusProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface EventBusModule {
    @Singleton
    @Binds
    fun provideBus(bus: EventBusProviderImpl): EventBusProvider
}