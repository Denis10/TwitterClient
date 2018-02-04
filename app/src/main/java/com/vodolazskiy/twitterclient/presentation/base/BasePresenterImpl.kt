package com.vodolazskiy.twitterclient.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter

abstract class BasePresenterImpl<V : BaseView>(private val subscriptions: SubscriptionContainer) :
        MvpQueuingBasePresenter<V>(), BasePresenter<V>,
        SubscriptionContainer by subscriptions {

    constructor() : this(SubscriptionsContainerDelegate())

    override fun destroy() {
        super.destroy()
        subscriptions.clearSubscriptions()
    }

    override fun viewStarted(view: V) {

    }

    override fun viewResumed(view: V) {

    }

    override fun viewPaused() {

    }

    override fun viewStopped() {

    }
}