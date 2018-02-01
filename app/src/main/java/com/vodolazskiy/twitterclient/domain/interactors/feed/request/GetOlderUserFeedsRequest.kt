package com.vodolazskiy.twitterclient.domain.interactors.feed.request

data class GetOlderUserFeedsRequest(val limit: Int, val maxId: Long?): DomainRequest