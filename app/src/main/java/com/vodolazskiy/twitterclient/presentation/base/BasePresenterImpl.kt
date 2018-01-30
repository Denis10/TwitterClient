package com.vodolazskiy.twitterclient.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BasePresenterImpl<V : MvpView>(private val subscriptions: SubscriptionContainer) :
        MvpQueuingBasePresenter<V>(),
        SubscriptionContainer by subscriptions {

    constructor() : this(SubscriptionsContainerDelegate())

    override fun destroy() {
        super.destroy()
        subscriptions.clearSubscriptions()
    }
}