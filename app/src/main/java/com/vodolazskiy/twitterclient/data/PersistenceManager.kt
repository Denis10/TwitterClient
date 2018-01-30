package com.vodolazskiy.twitterclient.data

import io.reactivex.Completable

interface PersistenceManager {
    fun deleteAll(): Completable
}