package com.vodolazskiy.twitterclient.presentation.screens.login

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.ioToMain
import com.vodolazskiy.twitterclient.domain.interactors.login.OpenZoneInteractor
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import com.vodolazskiy.twitterclient.presentation.base.bind
import javax.inject.Inject

class LoginPresenterImpl : BasePresenterImpl<LoginView>(), LoginPresenter {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var openZoneInteractor: OpenZoneInteractor

    init {
        DI.component.inject(this)
    }

    override fun login() {
        openZoneInteractor.login()
                .ioToMain()
                .subscribe({
                    onceViewAttached { it.openFeedScreen() }
                }, { exception ->
                    L.exception(exception)
                    onceViewAttached { it.showLoginError(exception.localizedMessage) }
                })
                .bind(this)
    }
}
