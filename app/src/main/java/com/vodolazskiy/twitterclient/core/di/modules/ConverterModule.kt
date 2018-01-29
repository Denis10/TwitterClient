package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.data.converter.DataConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConverterModule {
    //todo add separate converters for different layers using @Named annotation
    @Singleton
    @Provides
    fun provideConverter(): ConvertersContext = DataConverter()

//    @Singleton
//    @Provides
//    fun provideUserConverter(): CompositeConverter = UserFeedConverter()
//
//    @Singleton
//    @Provides
//    fun provideLoginConverter(): CompositeConverter = LoginConverter()
}