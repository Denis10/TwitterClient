package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.core.di.modules.activity.FeedActivityModule
import com.vodolazskiy.twitterclient.core.di.scopes.ActivityScope
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedActivity
import com.vodolazskiy.twitterclient.presentation.screens.login.LoginActivity
import com.vodolazskiy.twitterclient.presentation.screens.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [(AndroidSupportInjectionModule::class)])
interface AndroidModule {

    @ActivityScope
    @ContributesAndroidInjector()
    fun splashInjector(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector()
    fun loginInjector(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeedActivityModule::class])
    fun feedInjector(): FeedActivity
}
