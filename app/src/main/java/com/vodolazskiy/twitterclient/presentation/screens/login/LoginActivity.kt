package com.vodolazskiy.twitterclient.presentation.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.base.BaseActivity
import com.vodolazskiy.twitterclient.presentation.screens.feed.FeedActivityManager
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginView, LoginPresenter>(), LoginView {

    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var feedActivityManager: FeedActivityManager

    override fun createPresenter(): LoginPresenter {
        return LoginPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        btnTwitterLogin.setOnClickListener { presenter.login(activity = this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.sendActivityResult(requestCode, resultCode, data)
    }

    override fun openFeedScreen() {
        feedActivityManager.start(context = this)
        finish()
    }

    override fun showLoginError(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}