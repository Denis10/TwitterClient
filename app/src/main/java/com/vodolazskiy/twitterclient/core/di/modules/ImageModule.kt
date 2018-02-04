package com.vodolazskiy.twitterclient.core.di.modules

import com.vodolazskiy.twitterclient.presentation.glide.ImageBinder
import com.vodolazskiy.twitterclient.presentation.glide.ImageBinderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ImageModule {

    @Singleton
    @Binds
    fun provideGlide(handler: ImageBinderImpl): ImageBinder
}