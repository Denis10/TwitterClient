package com.vodolazskiy.twitterclient.app

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

@Suppress("ProtectedInFinal")
class App : Application(), HasActivityInjector {
    @Inject
    protected lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    protected lateinit var buildConfig: IBuildConfigInfoProvider

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent
                .builder()
                .context(this)
                .build()
        component.inject(this)
        DI.init(component)

        if (buildConfig.isDebug) {
            val policy = StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            StrictMode.setVmPolicy(policy)
            val threadPolicy = StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build()
            StrictMode.setThreadPolicy(threadPolicy)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onTerminate() {
        DI.destroy()
        super.onTerminate()
    }
}