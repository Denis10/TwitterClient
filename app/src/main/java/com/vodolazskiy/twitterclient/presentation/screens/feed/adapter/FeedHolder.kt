package com.vodolazskiy.twitterclient.presentation.screens.feed.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.core.di.DI
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.adapter.BasePaginationRVAdapter
import com.vodolazskiy.twitterclient.presentation.glide.ImageBinder
import kotlinx.android.synthetic.main.item_feed.view.*
import javax.inject.Inject

class FeedHolder private constructor(itemView: View) : BasePaginationRVAdapter.BaseVH<UserFeed>(itemView) {

    @Suppress("ProtectedInFinal")
    @Inject
    protected lateinit var imageBinder: ImageBinder

    init {
        DI.component.inject(this)
    }

    override fun bind(item: UserFeed) {

        itemView.txtTweet.text = item.text

        if (item.mediaUrlHttps.isNullOrEmpty()) {
            itemView.imgTweet.visibility = View.GONE
        } else {
            itemView.imgTweet.visibility = View.VISIBLE
            imageBinder.loadImage(itemView.imgTweet, item.mediaUrlHttps)
        }
    }

    private fun handleViewSize(view: View, width: Int, height: Int) {
        if (width > 0 && height > 0) {
            if (width >= height) {
                view.layoutParams.width = dpToPxInt(view.context, DEFAULT_WIDTH)
                val coef = (height.toFloat() / width * DEFAULT_WIDTH) as Int
                view.layoutParams.height = dpToPxInt(view.context, coef)
            } else {
                view.layoutParams.height = dpToPxInt(view.context, DEFAULT_WIDTH)
                val coef = (width.toFloat() / height * DEFAULT_WIDTH) as Int
                view.layoutParams.width = dpToPxInt(view.context, coef)
            }
        } else {
            view.layoutParams.width = dpToPxInt(view.context, DEFAULT_WIDTH)
            view.layoutParams.height = dpToPxInt(view.context, DEFAULT_WIDTH)
        }
    }

    private fun dpToPxInt(context: Context, dp: Int): Int {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics) as Int
    }

    companion object {
        private const val DEFAULT_WIDTH = 240

        fun create(parent: ViewGroup): FeedHolder {
            val inflater = LayoutInflater.from(parent.context)

            return FeedHolder(inflater.inflate(R.layout.item_feed, parent, false))
        }
    }
}