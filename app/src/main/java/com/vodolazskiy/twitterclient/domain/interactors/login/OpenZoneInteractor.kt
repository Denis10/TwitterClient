package com.vodolazskiy.twitterclient.domain.interactors.login

import android.app.Activity
import android.content.Intent
import io.reactivex.Completable
import io.reactivex.Observable

interface OpenZoneInteractor {
    fun login(activity: Activity): Observable<Unit>

    fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun getUserName(): Observable<String>

    fun logout(): Completable

    fun isLoggedIn(): Observable<Boolean>
}