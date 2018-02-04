package com.vodolazskiy.twitterclient.core.di.modules.activity

import com.vodolazskiy.twitterclient.core.di.scopes.FragmentScope
import com.vodolazskiy.twitterclient.presentation.screens.post.PostDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FeedActivityModule {
    @FragmentScope
    @ContributesAndroidInjector()
    fun providePostDialog(): PostDialog
}