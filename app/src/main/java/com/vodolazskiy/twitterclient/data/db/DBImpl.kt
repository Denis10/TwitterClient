package com.vodolazskiy.twitterclient.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDao
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity

@Database(entities = [
    UserFeedDbEntity::class],
        version = 1)
abstract class DBImpl : RoomDatabase() {
    //generated methods
    abstract fun getUserFeedDao(): UserFeedDao
}