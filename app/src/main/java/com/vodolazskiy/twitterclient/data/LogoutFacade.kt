package com.vodolazskiy.twitterclient.data

import io.reactivex.Completable

interface LogoutFacade {
    fun deleteAll(): Completable
}