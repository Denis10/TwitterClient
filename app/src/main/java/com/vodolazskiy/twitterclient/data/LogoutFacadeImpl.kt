package com.vodolazskiy.twitterclient.data

import com.twitter.sdk.android.core.TwitterCore
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.prefs.PrefsStorage
import io.reactivex.Completable
import javax.inject.Inject

@Suppress("ProtectedInFinal")
internal class LogoutFacadeImpl constructor(private val userFeed: UserFeedRepository, private val prefs: PrefsStorage) :
        LogoutFacade {

    override fun deleteAll(): Completable {
        return userFeed.removeAll()
                .andThen(prefs.clear())
                .andThen { TwitterCore.getInstance().sessionManager.clearActiveSession() }
    }
}