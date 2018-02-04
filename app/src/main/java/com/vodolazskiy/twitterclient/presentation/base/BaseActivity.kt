package com.vodolazskiy.twitterclient.presentation.base

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.v4.app.Fragment
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<V, P> : MvpActivity<V, P>(),
        GenericLifecycleObserver, BaseView,
        HasSupportFragmentInjector
        where V : BaseView, P : BasePresenter<V> {

    @Suppress("MemberVisibilityCanBePrivate")
    @Inject
    protected lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        lifecycle.addObserver(this)
    }

    override
    fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.viewStarted(mvpView)
            Lifecycle.Event.ON_RESUME -> presenter.viewResumed(mvpView)
            Lifecycle.Event.ON_PAUSE -> presenter.viewPaused()
            Lifecycle.Event.ON_STOP -> presenter.viewStopped()
            else -> {
                //do nothing
            }
        }
    }
}