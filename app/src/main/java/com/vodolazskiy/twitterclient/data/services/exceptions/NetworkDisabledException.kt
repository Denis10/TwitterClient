package com.vodolazskiy.twitterclient.data.services.exceptions

class NetworkDisabledException : NetworkException {

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)

}