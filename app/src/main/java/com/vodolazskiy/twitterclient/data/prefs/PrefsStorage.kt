package com.vodolazskiy.twitterclient.data.prefs

import io.reactivex.Completable

interface PrefsStorage {

    var twitterToken: String?
    fun clear(): Completable
}