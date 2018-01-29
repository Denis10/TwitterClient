package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.app.AppInfo
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProviderImpl
import com.vodolazskiy.twitterclient.app.IAppInfoProvider
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {

    @Singleton
    @Binds
    fun provideBuildConfig(config: BuildConfigInfoProviderImpl): BuildConfigInfoProvider

    @Singleton
    @Binds
    fun provideAppInfo(appInfo: AppInfo): IAppInfoProvider
}