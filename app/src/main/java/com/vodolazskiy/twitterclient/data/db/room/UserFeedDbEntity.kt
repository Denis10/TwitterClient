package com.vodolazskiy.twitterclient.data.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import java.util.*

const val USER_FEED_TABLE_NAME = "UserFeed"

@Entity(tableName = USER_FEED_TABLE_NAME)
data class UserFeedDbEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        override val id: Long,
        @ColumnInfo(name = "createdAt")
        override val createdAt: Date,
        @ColumnInfo(name = "text")
        override val text: String,
        @ColumnInfo(name = "photoUrl")
        override val mediaUrlHttps: String?
) : UserFeedEntity
