package com.vodolazskiy.twitterclient.data.services.exceptions


abstract class NetworkException : Exception {

    private var statusCode: Int = 0

    constructor()

    constructor(detailMessage: String) : super(detailMessage)

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)

    constructor(throwable: Throwable) : super(throwable)

    constructor(detailMessage: String, throwable: Throwable, code: Int) : super(detailMessage, throwable) {
        this.statusCode = code
    }


}