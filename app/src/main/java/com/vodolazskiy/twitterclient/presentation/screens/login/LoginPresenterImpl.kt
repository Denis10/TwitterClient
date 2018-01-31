package com.vodolazskiy.twitterclient.presentation.screens.login

import android.app.Activity
import android.content.Intent
import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.ioToMain
import com.vodolazskiy.twitterclient.domain.interactors.login.LoginInteractor
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import com.vodolazskiy.twitterclient.presentation.base.bind
import javax.inject.Inject

class LoginPresenterImpl : BasePresenterImpl<LoginView>(), LoginPresenter {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var loginInteractor: LoginInteractor

    init {
        DI.component.inject(this)
    }

    override fun sendActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        loginInteractor.callActivityResult(requestCode, resultCode, data)
    }

    override fun login(activity: Activity) {
        loginInteractor.login(activity)
                .ioToMain()
                .subscribe({
                    onceViewAttached { it.openFeedScreen() }
                }, { L .exception(it) })
                .bind(this)
    }
}