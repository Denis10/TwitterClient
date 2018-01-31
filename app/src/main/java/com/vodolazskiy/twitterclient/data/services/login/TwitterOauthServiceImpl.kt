package com.vodolazskiy.twitterclient.data.services.login

import android.app.Activity
import android.content.Intent
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.data.services.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.data.services.login.responses.LoginDataResponse
import io.reactivex.Observable

class TwitterOauthServiceImpl constructor(@DataConverterQualifier private val converter: ConvertersContext,
                                          private val handler: NetworkExceptionHandler) :
        TwitterAuthClient(), TwitterOauthService {

    override fun callActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        onActivityResult(requestCode, resultCode, data)
    }

    override fun callAuthorize(activity: Activity): Observable<LoginDataResponse> {
        return Observable.create { subscriber ->
            val callback = object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    subscriber.onNext(converter.convert(result.data, LoginDataResponse::class.java))
                    subscriber.onComplete()
                }

                override fun failure(exception: TwitterException) {
                    subscriber.onError(handler.handle(exception))
                }
            }

            authorize(activity, callback)
        }
    }
}