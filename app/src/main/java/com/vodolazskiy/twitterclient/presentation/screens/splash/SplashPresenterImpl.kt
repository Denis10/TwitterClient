package com.vodolazskiy.twitterclient.presentation.screens.splash

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.ioToMain
import com.vodolazskiy.twitterclient.domain.interactors.login.OpenZoneInteractor
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import com.vodolazskiy.twitterclient.presentation.base.bind
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenterImpl : BasePresenterImpl<SplashView>(), SplashPresenter {

    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var openZoneInteractor: OpenZoneInteractor

    init {
        DI.component.inject(this)
    }

    override fun attachView(view: SplashView) {
        super.attachView(view)

        openZoneInteractor.isLoggedIn()
                .ioToMain()
                .delay(1, TimeUnit.SECONDS)
                .subscribe({
                    if (it) {
                        onceViewAttached { it.openFeedScreen() }
                    } else {
                        onceViewAttached { it.openLoginScreen() }
                    }
                }, { L.exception(it) })
                .bind(this)
    }
}