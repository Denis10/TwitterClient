package com.vodolazskiy.twitterclient.data.modelinterfaces

import java.util.*

//todo add fields
interface UserFeedEntity : DataModel {
    val id: Long
    val createdAt: Date
    val text: String
}