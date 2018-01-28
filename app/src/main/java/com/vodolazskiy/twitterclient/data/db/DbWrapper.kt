package com.vodolazskiy.twitterclient.data.db

import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by denis on 1/28/18.
 */
internal class DbWrapper @Inject constructor(): DB {
    @Inject
    protected lateinit var userFeed: UserFeedRepository

    override fun deleteAll(): Completable {
       return userFeed.removeAll()
    }
}