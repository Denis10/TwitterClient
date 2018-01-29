package com.vodolazskiy.twitterclient.domain.interactors.login

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable

interface LoginInteractor {
    fun login(activity: Activity): Observable<Unit>

    fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent)
}