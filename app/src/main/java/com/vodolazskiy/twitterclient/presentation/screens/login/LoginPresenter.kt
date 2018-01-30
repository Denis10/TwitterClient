package com.vodolazskiy.twitterclient.presentation.screens.login

import android.app.Activity
import android.content.Intent
import com.vodolazskiy.twitterclient.presentation.base.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun sendActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    fun login(activity: Activity)

}