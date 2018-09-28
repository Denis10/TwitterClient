package com.vodolazskiy.twitterclient.core.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import javax.inject.Inject

interface TopActivityProvider {

    fun activity(): Activity?
}

class TopActivityProviderImpl @Inject constructor() : Application.ActivityLifecycleCallbacks, TopActivityProvider {
    private var currentActivity: Activity? = null

    override fun activity(): Activity? = currentActivity

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity?) {
        if (activity == currentActivity) {
            currentActivity = null
        }
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }
}

