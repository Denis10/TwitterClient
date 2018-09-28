package com.vodolazskiy.twitterclient.data.services.exceptions

import javax.inject.Inject

interface ExceptionType {

    fun isNoInternet(throwable: Throwable): Boolean
}

class ExceptionTypeImpl @Inject constructor() : ExceptionType {

    override fun isNoInternet(throwable: Throwable) = throwable is NetworkException.NetworkDisabledException
}
