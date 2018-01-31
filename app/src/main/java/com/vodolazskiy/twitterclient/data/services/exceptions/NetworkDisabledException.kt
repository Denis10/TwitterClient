package com.vodolazskiy.twitterclient.data.services.exceptions

class NetworkDisabledException : NetworkException {
    constructor() {}

    constructor(detailMessage: String) : super(detailMessage) {}

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable) {}

    constructor(throwable: Throwable) : super(throwable) {}
}