package com.vodolazskiy.twitterclient.core.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.eventbus.EventBusProvider
import com.vodolazskiy.twitterclient.core.eventbus.events.OnNetworkConnected
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface NetworkConnectionChangeListener {

    fun register()

    fun unregister()
}

class NetworkConnectionChangeListenerImpl @Inject constructor(private val context: Context,
                                                              private val status: NetworkStatusImpl,
                                                              private val bus: EventBusProvider) :
        BroadcastReceiver(), NetworkConnectionChangeListener {

    private var connectionChangedSubscription: Disposable? = null

    private val connectionChangedSubject = PublishSubject.create<Boolean>()

    override fun onReceive(context: Context?, intent: Intent?) {
        connectionChangedSubject.onNext(status.isConnected)
    }

    private fun getFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)

        return filter
    }

    override fun register() {
        context.registerReceiver(this, getFilter())

        connectionChangedSubscription?.dispose()
        connectionChangedSubscription = connectionChangedSubject
                .distinctUntilChanged()
                .subscribe({ isNetworkEnabled ->
                    if (isNetworkEnabled) {
                        bus.post(OnNetworkConnected())
                    }
                }, { L.exception(it) })
    }

    override fun unregister() {
        context.unregisterReceiver(this)
        connectionChangedSubscription?.dispose()
    }
}
