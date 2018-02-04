package com.vodolazskiy.twitterclient.data.modelinterfaces

import java.util.*

interface UserFeedEntity : DataModel {
    val id: Long
    val createdAt: Date
    val text: String
    val mediaUrlHttps: String?
}