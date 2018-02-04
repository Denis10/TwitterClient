package com.vodolazskiy.twitterclient.data.services.openzone

import android.app.Activity
import android.content.Intent
import com.vodolazskiy.twitterclient.data.services.openzone.responses.LoginDataResponse
import io.reactivex.Observable


interface TwitterOauthService {

    fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun callAuthorize(activity: Activity): Observable<LoginDataResponse>
}