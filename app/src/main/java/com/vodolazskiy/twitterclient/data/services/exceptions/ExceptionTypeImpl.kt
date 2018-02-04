package com.vodolazskiy.twitterclient.data.services.exceptions

import javax.inject.Inject


class ExceptionTypeImpl @Inject constructor() : ExceptionType {

    override fun isNoInternet(throwable: Throwable): Boolean {
        return throwable is NetworkDisabledException
    }
}
