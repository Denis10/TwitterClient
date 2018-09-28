package com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces

import com.vodolazskiy.twitterclient.core.util.ActivityResultListener
import com.vodolazskiy.twitterclient.domain.datalayerobjects.responses.LoginDataResponse
import io.reactivex.Observable

interface TwitterOauthService : ActivityResultListener {

    fun callAuthorize(): Observable<LoginDataResponse>
}
