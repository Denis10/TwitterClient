package com.vodolazskiy.twitterclient.data.services

interface NetworkExceptionHandler {

    fun handle(throwable: Throwable): Throwable
}
