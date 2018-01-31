package com.vodolazskiy.twitterclient.data.modelinterfaces

import java.util.*

//todo add fields
interface UserFeedEntity : DataModel {
    val id: String
    val createdAt: Date
    val text: String
}