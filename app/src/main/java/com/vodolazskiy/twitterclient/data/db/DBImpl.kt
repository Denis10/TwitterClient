package com.vodolazskiy.twitterclient.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDao

/**
 * Created by denis on 1/28/18.
 */
@Database(entities = arrayOf(
        UserFeedDbEntity::class),
        version = 1)
abstract class DBImpl: RoomDatabase() {
    //generated methods
    abstract fun getUserFeedDao(): UserFeedDao
}