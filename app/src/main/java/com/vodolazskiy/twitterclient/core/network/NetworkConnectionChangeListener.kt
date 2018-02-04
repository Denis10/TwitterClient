package com.vodolazskiy.twitterclient.core.network

interface NetworkConnectionChangeListener {

    fun register()
    fun unregister()
}