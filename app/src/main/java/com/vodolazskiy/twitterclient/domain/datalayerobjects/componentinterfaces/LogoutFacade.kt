package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

import io.reactivex.Completable

interface LogoutFacade {
    fun deleteAll(): Completable
}