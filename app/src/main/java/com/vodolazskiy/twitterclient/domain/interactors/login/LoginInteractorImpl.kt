package com.vodolazskiy.twitterclient.domain.interactors.login

import android.app.Activity
import android.content.Intent
import com.vodolazskiy.twitterclient.data.prefs.PrefsStorage
import com.vodolazskiy.twitterclient.data.services.login.TwitterOauthService
import io.reactivex.Observable
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(private val storage: PrefsStorage,
                                              private val oauthService: TwitterOauthService) : LoginInteractor {

    override fun login(activity: Activity): Observable<Unit> {
        return oauthService.callAuthorize(activity)
                .doOnNext { storage.twitterToken = it.token }
                .flatMap { _ -> Observable.just(Unit) }
    }

    override fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        oauthService.callActivityResult(requestCode, resultCode, data)
    }
}