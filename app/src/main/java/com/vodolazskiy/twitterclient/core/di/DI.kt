package com.vodolazskiy.twitterclient.core.di

import android.content.Context

/**
 * Dependency injector class
 * Before using, call init() !!!
 * Also call destroy() when application is terminated
 */
object DI {
    private var appComponent: AppComponent? = null

    val context: Context get() = appComponent?.context ?: throw DependencyProviderException("Call init() method before!")
    val component: AppComponent get() = appComponent ?: throw DependencyProviderException("Call init() method before!")

    fun init(component: AppComponent) {
        appComponent = component
    }

    fun destroy() {
        if (appComponent != null) {
            appComponent = null
        }
    }

    class DependencyProviderException(s: String) : IllegalStateException(s)
}
