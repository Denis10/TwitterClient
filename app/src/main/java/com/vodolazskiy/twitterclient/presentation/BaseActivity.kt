package com.vodolazskiy.twitterclient.presentation

import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BaseActivity<V, P> : MvpActivity<V, P>()
        where V : MvpView, P : MvpPresenter<V> {

}