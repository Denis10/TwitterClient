package com.vodolazskiy.twitterclient.data.services.exceptions

class TooManyRequestsException(detailMessage: String, throwable: Throwable) : NetworkException(detailMessage, throwable)