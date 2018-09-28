package com.vodolazskiy.twitterclient.data.services.openzone

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.core.util.ActivityResultListener
import com.vodolazskiy.twitterclient.core.util.TopActivityProvider
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.NetworkExceptionHandler
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.TwitterOauthService
import com.vodolazskiy.twitterclient.domain.datalayerobjects.responses.LoginDataResponse
import io.reactivex.Observable

class TwitterOauthServiceImpl constructor(@DataConverterQualifier private val converter: ConvertersContext,
                                          private val handler: NetworkExceptionHandler,
                                          private val topActivityProvider: TopActivityProvider) :
        TwitterAuthClient(), TwitterOauthService {

    override fun onActivityResult(result: ActivityResultListener.ActivityResult) {
        onActivityResult(result.requestCode, result.resultCode, result.data)
    }

    override fun callAuthorize(): Observable<LoginDataResponse> {
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

            authorize(topActivityProvider.activity(), callback)
        }
    }
}
