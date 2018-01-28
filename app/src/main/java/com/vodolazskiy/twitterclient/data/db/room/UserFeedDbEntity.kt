package com.vodolazskiy.twitterclient.data.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeed

/**
 * Created by denis on 1/28/18.
 */
@Entity(tableName = UserFeedDbEntity.TABLE_NAME)
data class UserFeedDbEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        override val id: String
) : UserFeed {

    companion object {
        const val TABLE_NAME = "UserFeed"
    }
}