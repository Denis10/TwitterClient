package com.vodolazskiy.twitterclient.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface BasePresenter<V : BaseView> : MvpPresenter<V> {

    fun viewStarted(view: V)

    fun viewStopped()
}