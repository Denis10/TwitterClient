package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.converter.IConvertersContext
import com.vodolazskiy.twitterclient.data.converter.Converter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by denis on 1/28/18.
 */
@Module
class ConverterModule {
    //todo add separate converters for different layers
    @Singleton
    @Provides
    fun provideConverter(): IConvertersContext = Converter.get()
}