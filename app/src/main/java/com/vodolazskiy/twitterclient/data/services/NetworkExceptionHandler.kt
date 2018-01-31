package com.vodolazskiy.twitterclient.data.services

interface NetworkExceptionHandler {

    fun handle(throwable: Throwable): Throwable

    fun getCode(throwable: Throwable): Int

    fun getMessage(throwable: Throwable): String?

    companion object {
        const val UNDEF_ERROR_CODE = Integer.MIN_VALUE
    }
}
