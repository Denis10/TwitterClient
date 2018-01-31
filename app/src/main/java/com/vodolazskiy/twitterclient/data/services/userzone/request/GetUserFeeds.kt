package com.vodolazskiy.twitterclient.data.services.userzone.request

data class GetUserFeeds(val limit: Int, val sinceId: Long?, val maxId: Long?)