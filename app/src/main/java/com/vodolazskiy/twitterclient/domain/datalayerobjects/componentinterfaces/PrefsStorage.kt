package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

import io.reactivex.Completable

interface PrefsStorage {

    var twitterToken: String?
    var userName: String?
    fun clear(): Completable
}