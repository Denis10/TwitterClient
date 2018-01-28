package com.vodolazskiy.twitterclient.data.db

import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import io.reactivex.Completable
import javax.inject.Inject

@Suppress("ProtectedInFinal")
internal class DbManagerImpl : DbManager {
    @Inject
    protected lateinit var userFeed: UserFeedRepository

    override fun deleteAll(): Completable {
        return userFeed.removeAll()
    }
}