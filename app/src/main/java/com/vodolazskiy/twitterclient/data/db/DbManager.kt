package com.vodolazskiy.twitterclient.data.db

import io.reactivex.Completable

interface DbManager {
    fun deleteAll(): Completable
}