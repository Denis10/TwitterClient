package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.BaseConvertersContextImpl
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.core.di.annotation.LoginQualifier
import com.vodolazskiy.twitterclient.core.di.annotation.UserFeedEntityQualifier

internal class DataConverter constructor(@UserFeedEntityQualifier userFeedEntityConverter: CompositeConverter,
                                         @LoginQualifier loginConverter: CompositeConverter) :
        BaseConvertersContextImpl() {

    init {
        registerConverter(userFeedEntityConverter)
        registerConverter(loginConverter)
    }
}
