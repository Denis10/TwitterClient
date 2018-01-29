package com.vodolazskiy.twitterclient.presentation

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface BasePresenter<V : MvpView> : MvpPresenter<V> {
}