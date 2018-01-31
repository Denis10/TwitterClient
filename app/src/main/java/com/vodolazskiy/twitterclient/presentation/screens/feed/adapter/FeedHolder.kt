package com.vodolazskiy.twitterclient.presentation.screens.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vodolazskiy.twitterclient.R
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.BasePaginationRVAdapter
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedHolder private constructor(itemView: View) : BasePaginationRVAdapter.BaseVH<UserFeed>(itemView) {
    override fun bind(item: UserFeed) {

        itemView.txtTweet.text = item.text
        //todo add other fields
    }

    companion object {
        fun create(parent: ViewGroup): FeedHolder {
            val inflater = LayoutInflater.from(parent.context)

            return FeedHolder(inflater.inflate(R.layout.item_feed, parent, false))
        }
    }
}