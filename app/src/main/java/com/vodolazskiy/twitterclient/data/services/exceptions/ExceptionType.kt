package com.vodolazskiy.twitterclient.data.services.exceptions


interface ExceptionType {
    fun isNoInternet(throwable: Throwable): Boolean
}