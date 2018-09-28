package com.vodolazskiy.twitterclient.domain.interactors.login

import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.LogoutFacade
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.PrefsStorage
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

interface OpenZoneInteractor {

    fun login(): Observable<Unit>

    fun getUserName(): Observable<String>

    fun logout(): Completable

    fun isLoggedIn(): Observable<Boolean>
}

class OpenZoneInteractorImpl @Inject constructor(private val storage: PrefsStorage,
                                                 private val oauthService: TwitterOauthService,
                                                 private val logoutFacade: LogoutFacade) : OpenZoneInteractor {

    override fun login(): Observable<Unit> = oauthService.callAuthorize()
            .doOnNext {
                storage.twitterToken = it.token
                storage.userName = it.userName
            }
            .map { Unit }

    override fun getUserName(): Observable<String> =
            Observable.fromCallable { storage.userName ?: "" }

    override fun logout() = logoutFacade.deleteAll()

    override fun isLoggedIn(): Observable<Boolean> =
            Observable.fromCallable { !storage.twitterToken.isNullOrBlank() }
}
