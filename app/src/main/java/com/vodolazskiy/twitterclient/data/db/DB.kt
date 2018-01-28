package com.vodolazskiy.twitterclient.data.db

import io.reactivex.Completable

/**
 * Created by denis on 1/28/18.
 */
interface DB {
    fun deleteAll(): Completable
}