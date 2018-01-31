package com.vodolazskiy.twitterclient.data.prefs

import io.reactivex.Completable

interface PrefsStorage {

    var twitterToken: String?
    var userName: String?
    fun clear(): Completable
}