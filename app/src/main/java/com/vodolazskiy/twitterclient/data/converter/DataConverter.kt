package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.BaseConvertersContextImpl
import com.vodolazskiy.twitterclient.data.converter.composite.LoginConverter
import com.vodolazskiy.twitterclient.data.converter.composite.UserFeedConverter

internal class DataConverter : BaseConvertersContextImpl() {

    //todo use DI
    init {
        registerConverter(UserFeedConverter())
        registerConverter(LoginConverter())
    }
}
