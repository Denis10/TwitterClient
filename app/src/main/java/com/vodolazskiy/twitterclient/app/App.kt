package com.vodolazskiy.twitterclient.app

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.vodolazskiy.twitterclient.R
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
    protected lateinit var buildConfig: BuildConfigInfoProvider

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent
                .builder()
                .context(this)
                .build()
        component.inject(this)
        DI.init(component)

        val twitterKey = getString(R.string.twitter_key)
        val twitterSecret = getString(R.string.twitter_secret)
        Twitter.initialize(TwitterConfig.Builder(this)
                .twitterAuthConfig(TwitterAuthConfig(twitterKey, twitterSecret))
                .build())

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