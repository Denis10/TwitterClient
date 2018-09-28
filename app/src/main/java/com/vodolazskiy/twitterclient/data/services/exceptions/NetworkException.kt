package com.vodolazskiy.twitterclient.data.services.exceptions


sealed class NetworkException(detailMessage: String? = null, cause: Throwable? = null) : Exception(detailMessage, cause) {
    data class NetworkCommonException(val detailMessage: String, val throwable: Throwable) : NetworkException(detailMessage)
    data class NetworkDisabledException(val detailMessage: String, val throwable: Throwable) : NetworkException(detailMessage)
    data class ServerException(val detailMessage: String, val throwable: Throwable) : NetworkException(detailMessage)
    data class TooManyRequestsException(val detailMessage: String, val throwable: Throwable) : NetworkException(detailMessage)
}
