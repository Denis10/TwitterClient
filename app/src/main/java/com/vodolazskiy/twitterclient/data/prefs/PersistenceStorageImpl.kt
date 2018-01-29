package com.vodolazskiy.twitterclient.data.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PersistenceStorageImpl(context: Context) : PersistenceStorage {
    private val prefs: SharedPreferences = context.getSharedPreferences("TwitterClient", Context.MODE_PRIVATE)

    override var twitterToken: String?
        get() = prefs.getString("id", null)
        set(value) = prefs.edit().putString("id", value).apply()
}