package com.vodolazskiy.twitterclient.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface SubscriptionContainer {
    fun addSubscription(rxSubscription: Disposable): Disposable
    fun clearSubscriptions()
}

class SubscriptionsContainerDelegate : SubscriptionContainer {
    private val subs = CompositeDisposable()

    override fun addSubscription(rxSubscription: Disposable): Disposable {
        subs.add(rxSubscription)
        return rxSubscription
    }


    override fun clearSubscriptions() {
        subs.clear()
    }
}

fun Disposable.bind(subscriptionContainer: SubscriptionContainer): Disposable {
    subscriptionContainer.addSubscription(this)
    return this
}