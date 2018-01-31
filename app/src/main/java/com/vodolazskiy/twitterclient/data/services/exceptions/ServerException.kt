package com.vodolazskiy.twitterclient.data.services.exceptions

class ServerException (detailMessage: String, throwable: Throwable) : NetworkException(detailMessage, throwable) {
}