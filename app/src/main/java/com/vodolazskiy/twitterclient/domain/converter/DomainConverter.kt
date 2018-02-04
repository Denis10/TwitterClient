package com.vodolazskiy.twitterclient.domain.converter

import com.vodolazskiy.twitterclient.core.converter.BaseConvertersContextImpl
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.core.di.annotation.UserFeedQualifier

class DomainConverter constructor(@UserFeedQualifier userFeedConverter: CompositeConverter) : BaseConvertersContextImpl() {

    init {
        registerConverter(userFeedConverter)
    }
}