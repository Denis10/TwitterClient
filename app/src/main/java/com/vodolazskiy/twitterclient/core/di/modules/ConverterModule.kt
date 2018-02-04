package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.*
import com.vodolazskiy.twitterclient.data.converter.DataConverter
import com.vodolazskiy.twitterclient.data.converter.composite.LoginConverter
import com.vodolazskiy.twitterclient.data.converter.composite.UserFeedEntityConverter
import com.vodolazskiy.twitterclient.domain.converter.DomainConverter
import com.vodolazskiy.twitterclient.domain.converter.composite.UserFeedConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConverterModule {

    /*
    * Data converter
    */

    @DataConverterQualifier
    @Singleton
    @Provides
    fun provideDataConverter(@UserFeedEntityQualifier userFeedEntityConverter: CompositeConverter,
                             @LoginQualifier loginConverter: CompositeConverter): ConvertersContext =
            DataConverter(userFeedEntityConverter, loginConverter)

    @UserFeedEntityQualifier
    @Singleton
    @Provides
    fun provideUserFeedEntityConverter(): CompositeConverter = UserFeedEntityConverter()

    @LoginQualifier
    @Singleton
    @Provides
    fun provideLoginConverter(): CompositeConverter = LoginConverter()

    /*
    * Domain converter
    */

    @DomainConverterQualifier
    @Singleton
    @Provides
    fun provideDomainConverter(@UserFeedQualifier userFeedConverter: CompositeConverter): ConvertersContext =
            DomainConverter(userFeedConverter)

    @UserFeedQualifier
    @Singleton
    @Provides
    fun provideUserFeedConverter(): CompositeConverter = UserFeedConverter()
}