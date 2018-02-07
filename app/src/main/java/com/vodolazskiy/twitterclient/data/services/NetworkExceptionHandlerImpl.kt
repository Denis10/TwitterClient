package com.vodolazskiy.twitterclient.data.services

import android.content.Context
import com.twitter.sdk.android.core.TwitterApiException
import com.twitter.sdk.android.core.TwitterException
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.data.services.exceptions.*
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.NetworkExceptionHandler
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
        return when {
            isNetworkDisabledException(throwable) -> NetworkDisabledException(appContext.getString(R.string.no_internet_try_later), throwable)
            throwable is TwitterApiException -> return when {
                isServerError(throwable.statusCode) -> ServerException(appContext.getString(R.string.server_error), throwable)
                TOO_MANY_REQUESTS == throwable.statusCode -> TooManyRequestsException(appContext.getString(R.string.too_many_requests), throwable)
                else -> NetworkCommonException(getMessage(throwable), throwable)
            }
            else -> NetworkCommonException(getMessage(throwable), throwable)
        }
    }

    private fun getMessage(throwable: Throwable): String {
        if (throwable is TwitterException) {
            appContext.getString(R.string.twitter_exception)
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
                || throwable.cause is SocketTimeoutException || throwable.cause is UnknownHostException
    }

    private fun isServerError(code: Int): Boolean {
        return when (code) {
            SERVER_ERROR_500, SERVER_ERROR_501, SERVER_ERROR_502, SERVER_ERROR_503, SERVER_ERROR_504, SERVER_ERROR_509 -> true
            else -> false
        }
    }

    companion object {
        private const val SERVER_ERROR_500 = 500
        private const val SERVER_ERROR_501 = 501
        private const val SERVER_ERROR_502 = 502
        private const val SERVER_ERROR_503 = 503
        private const val SERVER_ERROR_504 = 504
        private const val SERVER_ERROR_509 = 509
        private const val TOO_MANY_REQUESTS = 429
    }
}
