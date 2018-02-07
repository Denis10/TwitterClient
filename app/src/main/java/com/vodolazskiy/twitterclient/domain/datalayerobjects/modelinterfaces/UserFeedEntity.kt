package com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces

import java.util.*

interface UserFeedEntity : DataModel {
    val id: Long
    val createdAt: Date
    val text: String
    val mediaUrlHttps: String?
}