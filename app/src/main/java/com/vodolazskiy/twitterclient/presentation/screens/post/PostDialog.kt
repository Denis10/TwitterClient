package com.vodolazskiy.twitterclient.presentation.screens.post

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.presentation.base.BaseDialogFragment
import kotlinx.android.synthetic.main.screen_post.*


class PostDialog : BaseDialogFragment<PostView, PostPresenter>(), PostView {
    private lateinit var callback: PostCallback

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
           tilTweet.error = ""
        }
    }


    override fun createPresenter(): PostPresenter = PostPresenterImpl()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as PostCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle(R.string.post_tweet)

        return inflater.inflate(R.layout.screen_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtTweet.addTextChangedListener(watcher)

        btnPost.setOnClickListener {
            val text = edtTweet.text.toString()
            if (text.isNotBlank()) {
                presenter.postTweet(edtTweet.text.toString())
            } else {
                tilTweet.error = getString(R.string.empty_field)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun closeScreen() {
        dismiss()
        callback.tweetPosted()
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        postProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        postProgress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        edtTweet.removeTextChangedListener(watcher)
    }

    companion object Factory {
        fun newInstance(): PostDialog {
            val dialog = PostDialog()
            val args = Bundle()
            dialog.arguments = args

            return dialog
        }
    }
}