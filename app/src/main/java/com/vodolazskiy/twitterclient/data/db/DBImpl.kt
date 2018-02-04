package com.vodolazskiy.twitterclient.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.vodolazskiy.twitterclient.data.db.room.DbConverters
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDao
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity

@Database(entities = [
    UserFeedDbEntity::class],
        version = 1)
@TypeConverters(DbConverters::class)
abstract class DBImpl : RoomDatabase() {
    //generated methods
    abstract fun getUserFeedDao(): UserFeedDao
}