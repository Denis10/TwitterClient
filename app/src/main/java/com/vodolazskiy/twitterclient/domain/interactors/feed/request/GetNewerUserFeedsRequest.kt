package com.vodolazskiy.twitterclient.domain.interactors.feed.request

data class GetNewerUserFeedsRequest(val limit: Int, val sinceId: Long): DomainRequest