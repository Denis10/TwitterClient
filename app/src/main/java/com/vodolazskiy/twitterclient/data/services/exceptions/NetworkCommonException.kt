package com.vodolazskiy.twitterclient.data.services.exceptions

class NetworkCommonException: NetworkException {

    constructor() {}

    constructor(detailMessage: String) : super(detailMessage) {}

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable) {}

    constructor(throwable: Throwable) : super(throwable) {}
}