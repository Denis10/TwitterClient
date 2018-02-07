package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

interface NetworkExceptionHandler {

    fun handle(throwable: Throwable): Throwable
}
