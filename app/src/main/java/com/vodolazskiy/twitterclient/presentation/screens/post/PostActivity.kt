package com.vodolazskiy.twitterclient.presentation.screens.post

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : BaseActivity<PostView, PostPresenter>(), PostView {
    override fun createPresenter(): PostPresenter = PostPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post)
        setTitle(R.string.post_tweet)
    }

    override fun closeScreen() {
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(){
        postProgress.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        postProgress.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.post_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.postItem -> {
            val text = edtTweet.text.toString()
            if (text.isNotBlank()) {
                presenter.postTweet(edtTweet.text.toString())
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}