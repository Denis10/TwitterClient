package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.app.AppInfo
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProviderImpl
import com.vodolazskiy.twitterclient.app.IAppInfoProvider
import com.vodolazskiy.twitterclient.app.IBuildConfigInfoProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by denis on 1/28/18.
 */
@Module
interface AppModule {

    @Singleton
    @Binds
    fun provideBuildConfig(config: BuildConfigInfoProviderImpl): IBuildConfigInfoProvider

    @Singleton
    @Binds
    fun provideAppInfo(appInfo: AppInfo): IAppInfoProvider
}