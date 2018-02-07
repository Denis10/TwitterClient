package com.vodolazskiy.twitterclient.domain.datalayerobjects.request

import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.DataRequest

data class PostTweetDataRequest(val text: String) : DataRequest