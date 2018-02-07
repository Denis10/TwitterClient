package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.network.NetworkConnectionChangeListener
import com.vodolazskiy.twitterclient.core.network.NetworkConnectionChangeListenerImpl
import com.vodolazskiy.twitterclient.core.network.NetworkStatus
import com.vodolazskiy.twitterclient.core.network.NetworkStatusImpl
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.data.services.NetworkExceptionHandlerImpl
import com.vodolazskiy.twitterclient.data.services.exceptions.ExceptionType
import com.vodolazskiy.twitterclient.data.services.exceptions.ExceptionTypeImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Binds
    fun provideErrorHandler(handler: NetworkExceptionHandlerImpl): NetworkExceptionHandler

    @Singleton
    @Binds
    fun provideExceptionType(exceptionType: ExceptionTypeImpl): ExceptionType

    @Singleton
    @Binds
    fun provideNetworkStatus(status: NetworkStatusImpl): NetworkStatus

    @Singleton
    @Binds
    fun provideNetworkListener(listener: NetworkConnectionChangeListenerImpl): NetworkConnectionChangeListener
}