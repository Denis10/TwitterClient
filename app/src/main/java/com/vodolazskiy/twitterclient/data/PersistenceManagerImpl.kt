package com.vodolazskiy.twitterclient.data

import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.prefs.PrefsStorage
import io.reactivex.Completable
import javax.inject.Inject

@Suppress("ProtectedInFinal")
internal class PersistenceManagerImpl : PersistenceManager {
    @Inject
    protected lateinit var userFeed: UserFeedRepository
    @Inject
    protected lateinit var prefs: PrefsStorage

    override fun deleteAll(): Completable {
        return userFeed.removeAll()
                .andThen(prefs.clear())
    }
}