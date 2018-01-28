package com.vodolazskiy.twitterclient.data.services

import com.twitter.sdk.android.core.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by denis on 1/28/18.
 */
interface UserService {

    @GET("/1.1/users/show.json")
    fun show(@Query("user_id") id: Long): Call<User>
}