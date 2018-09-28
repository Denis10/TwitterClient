package com.vodolazskiy.twitterclient.core.di

import android.content.Context
import com.vodolazskiy.twitterclient.app.App
import com.vodolazskiy.twitterclient.core.di.modules.*
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
    ScreensProviderModule::class,
    EventBusModule::class,
    NetworkModule::class,
    ImageModule::class,
    TwitterActivityResultModule::class,
    ActivityUtils::class
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
