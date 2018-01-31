package com.vodolazskiy.twitterclient.domain.converter.models

import android.os.Parcelable
import java.util.*


interface UserFeed: Parcelable {
    val id: String
    val createdAt: Date
    val text: String
}