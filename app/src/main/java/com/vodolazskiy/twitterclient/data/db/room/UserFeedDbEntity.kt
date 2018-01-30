package com.vodolazskiy.twitterclient.data.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeedEntity

@Entity(tableName = UserFeedDbEntity.TABLE_NAME)
data class UserFeedDbEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        override val id: String
) : UserFeedEntity {

    companion object {
        const val TABLE_NAME = "UserFeed"
    }
}