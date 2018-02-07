package com.vodolazskiy.twitterclient.domain.datalayerobjects.request

import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.DataRequest

data class GetUserFeedsDataRequest(val limit: Int, val sinceId: Long?, val maxId: Long?) : DataRequest