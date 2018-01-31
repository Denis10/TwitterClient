package com.vodolazskiy.twitterclient.domain.converter.models

import android.os.Parcelable
import java.util.*


interface UserFeed: Parcelable {
    val id: Long
    val createdAt: Date
    val text: String
}