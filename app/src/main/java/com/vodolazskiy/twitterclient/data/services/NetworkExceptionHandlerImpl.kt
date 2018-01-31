package com.vodolazskiy.twitterclient.data.services

import android.content.Context
import com.twitter.sdk.android.core.TwitterException
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.data.services.exceptions.NetworkCommonException
import com.vodolazskiy.twitterclient.data.services.exceptions.NetworkDisabledException
import com.vodolazskiy.twitterclient.data.services.exceptions.NetworkException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class NetworkExceptionHandlerImpl @Inject constructor(private val appContext: Context) :
        NetworkExceptionHandler {


    override fun handle(throwable: Throwable): Throwable {
        L.exception(throwable)

        if (throwable is NetworkException) { //error is already handled
            return throwable
        }
        return if (isNetworkDisabledException(throwable)) {
            NetworkDisabledException(appContext.getString(R.string.no_internet_try_later), throwable)
        } else NetworkCommonException(getMessage(throwable), throwable)
    }

    override fun getCode(throwable: Throwable): Int {
        return NetworkExceptionHandler.UNDEF_ERROR_CODE
    }

    override fun getMessage(throwable: Throwable): String {
        if (throwable is TwitterException) {
            L.d("TwitterException", throwable)
        } else {
            //network error
            if (throwable.cause != null && "Canceled".equals(throwable.cause!!.message, ignoreCase = true)) {
                return ""
            } else if (isNetworkDisabledException(throwable)) {
                //no network exception
                return appContext.getString(R.string.no_internet_try_later)
            }
        }

        return appContext.getString(R.string.something_went_wrong_title)
    }

    private fun isNetworkDisabledException(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException || throwable is UnknownHostException
    }
}
