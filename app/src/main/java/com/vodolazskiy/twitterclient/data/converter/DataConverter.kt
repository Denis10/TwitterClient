package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.BaseConvertersContextImpl

internal class DataConverter : BaseConvertersContextImpl() {

    init {
        registerConverter(UserFeedConverter())
    }
}
