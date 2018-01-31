package com.vodolazskiy.twitterclient.domain.interactors.feed.request

data class GetNewerUserFeeds(val limit: Int, val sinceId: Long)