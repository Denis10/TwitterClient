package com.vodolazskiy.twitterclient.presentation.screens.login

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.vodolazskiy.twitterclient.data.services.openzone.responses.LoginDataResponse
import io.reactivex.Single

class LoginCallback : Callback<TwitterSession>() {

    val observer = Single.create<LoginDataResponse> { emiter ->

    }

    override fun success(result: Result<TwitterSession>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun failure(exception: TwitterException) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}