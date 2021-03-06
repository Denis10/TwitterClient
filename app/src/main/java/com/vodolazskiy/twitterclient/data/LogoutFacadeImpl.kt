package com.vodolazskiy.twitterclient.data

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.LogoutFacade
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.PrefsStorage
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.UserFeedRepository
import io.reactivex.Completable

@Suppress("ProtectedInFinal")
internal class LogoutFacadeImpl constructor(private val userFeed: UserFeedRepository, private val prefs: PrefsStorage) :
        LogoutFacade {

    override fun deleteAll(): Completable =
            userFeed.removeAll()
                    .andThen(prefs.clear())
                    .andThen {
                        TwitterCore.getInstance().sessionManager.clearActiveSession()
                        it.onComplete()
                    }
}
