package com.vodolazskiy.twitterclient.presentation

import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BasePresenterImpl<V: MvpView>: MvpQueuingBasePresenter<V>()