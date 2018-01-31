package com.vodolazskiy.twitterclient.domain.converter.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class UserFeedItem(override val id: Long, override val createdAt: Date, override val text: String) : UserFeed {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            Date(parcel.readLong()),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(createdAt.time)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserFeedItem> {
        override fun createFromParcel(parcel: Parcel): UserFeedItem {
            return UserFeedItem(parcel)
        }

        override fun newArray(size: Int): Array<UserFeedItem?> {
            return arrayOfNulls(size)
        }
    }
}