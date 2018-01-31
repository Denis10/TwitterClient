package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.App
import com.vodolazskiy.twitterclient.app.BuildConfigInfoProvider
import com.vodolazskiy.twitterclient.core.di.modules.*
import com.vodolazskiy.twitterclient.presentation.base.PaginationTool
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidModule::class,
    ConverterModule::class,
    PersistenceModule::class,
    TwitterServiceModule::class,
    InteractorModule::class,
    ActivityProviderModule::class,
    EventBusModule::class,
    NetworkModule::class
])
interface AppComponent : AppGraph {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}