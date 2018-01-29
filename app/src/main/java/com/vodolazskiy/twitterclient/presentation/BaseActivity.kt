package com.vodolazskiy.twitterclient.presentation

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import dagger.android.AndroidInjection

abstract class BaseActivity<V, P> : MvpActivity<V, P>()
        where V : MvpView, P : MvpPresenter<V> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }
}