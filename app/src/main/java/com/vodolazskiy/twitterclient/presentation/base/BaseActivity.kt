package com.vodolazskiy.twitterclient.presentation.base

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import dagger.android.AndroidInjection

abstract class BaseActivity<V, P> : MvpActivity<V, P>(),
        GenericLifecycleObserver, BaseView
        where V : BaseView, P : BasePresenter<V> {

    init {
        lifecycle.addObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        mvpDelegate.onStart()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.viewStarted(mvpView)
            Lifecycle.Event.ON_STOP -> presenter.viewStopped()
            else -> {
                //do nothing
            }
        }
    }
}