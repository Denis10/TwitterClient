package com.vodolazskiy.twitterclient.data.services.userzone

import com.twitter.sdk.android.core.models.User
import com.vodolazskiy.twitterclient.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/${BuildConfig.TWITTER_API_VERSION}/users/show.json")
    fun show(@Query("user_id") id: Long): Call<User>
}