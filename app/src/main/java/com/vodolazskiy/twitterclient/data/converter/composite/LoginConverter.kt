package com.vodolazskiy.twitterclient.data.converter.composite

import com.twitter.sdk.android.core.TwitterSession
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.registerConverter
import com.vodolazskiy.twitterclient.data.services.openzone.responses.LoginDataResponse

class LoginConverter : CompositeConverter {
    override fun register(convertersContext: ConvertersContext) {
        convertersContext.registerConverter(TwitterSession::class.java, LoginDataResponse::class.java, this::toLoginDataResponse)
    }

    private fun toLoginDataResponse(inItem: TwitterSession): LoginDataResponse {
        return LoginDataResponse(inItem.authToken.token, inItem.userName)
    }
}