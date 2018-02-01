package com.vodolazskiy.twitterclient.data.services.userzone.request

import com.vodolazskiy.twitterclient.data.modelinterfaces.DataRequest

data class GetUserFeedsDataRequest(val limit: Int, val sinceId: Long?, val maxId: Long?): DataRequest