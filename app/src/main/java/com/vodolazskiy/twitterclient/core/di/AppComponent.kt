package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.App
import com.vodolazskiy.twitterclient.app.IBuildConfigInfoProvider
import com.vodolazskiy.twitterclient.core.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidModule::class,
    ConverterModule::class,
    DbModule::class,
    TwitterServiceModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    val context: Context
    val buildConfig: IBuildConfigInfoProvider
}