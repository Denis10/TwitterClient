package com.vodolazskiy.twitterclient.presentation.screens.post

import com.vodolazskiy.twitterclient.core.L
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.core.ioToMain
import com.vodolazskiy.twitterclient.domain.interactors.feed.UserFeedInteractor
import com.vodolazskiy.twitterclient.domain.interactors.feed.request.PostTweetRequest
import com.vodolazskiy.twitterclient.presentation.base.BasePresenterImpl
import com.vodolazskiy.twitterclient.presentation.base.bind
import javax.inject.Inject

class PostPresenterImpl : BasePresenterImpl<PostView>(), PostPresenter {
    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var feedInteractor: UserFeedInteractor

    init {
        DI.component.inject(this)
    }

    override fun postTweet(text: String) {
        onceViewAttached { it.showProgress() }
        feedInteractor.sendTweet(PostTweetRequest(text))
                .ioToMain()
                .subscribe({
                    onceViewAttached {
                        it.hideProgress()
                        it.closeScreen()
                    }
                }, { exception ->
                    L.exception(exception)
                    onceViewAttached {
                        it.hideProgress()
                        it.showError(exception.localizedMessage)
                    }
                })
                .bind(this)
    }
}