package com.vodolazskiy.twitterclient.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.PrefsStorage
import io.reactivex.Completable
import io.reactivex.internal.operators.completable.CompletableFromCallable

class PrefsStorageImpl(context: Context) : PrefsStorage {

    private val prefs: SharedPreferences = context.getSharedPreferences("TwitterClient", Context.MODE_PRIVATE)

    override var twitterToken: String?
        get() = prefs.getString("id", null)
        set(value) = prefs.edit().putString("id", value).apply()

    override var userName: String?
        get() = prefs.getString("user_name", null)
        set(value) = prefs.edit().putString("user_name", value).apply()

    override fun clear(): Completable = CompletableFromCallable { prefs.edit().clear().apply() }
}
