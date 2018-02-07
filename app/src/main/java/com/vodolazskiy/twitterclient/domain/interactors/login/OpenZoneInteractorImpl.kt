package com.vodolazskiy.twitterclient.domain.interactors.login

import android.app.Activity
import android.content.Intent
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.LogoutFacade
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.PrefsStorage
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class OpenZoneInteractorImpl @Inject constructor(private val storage: PrefsStorage,
                                                 private val oauthService: TwitterOauthService,
                                                 private val logoutFacade: LogoutFacade) : OpenZoneInteractor {

    override fun login(activity: Activity): Observable<Unit> {
        return oauthService.callAuthorize(activity)
                .doOnNext {
                    storage.twitterToken = it.token
                    storage.userName = it.userName
                }
                .map { Unit }
    }

    override fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        oauthService.callActivityResult(requestCode, resultCode, data)
    }


    override fun getUserName(): Observable<String> =
            Observable.fromCallable { storage.userName ?: "" }

    override fun logout(): Completable =
            logoutFacade.deleteAll()

    override fun isLoggedIn(): Observable<Boolean> =
            Observable.fromCallable { !storage.twitterToken.isNullOrBlank() }
}