package com.vodolazskiy.twitterclient.presentation.screens.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.adapter.BaseSortedPaginationRVAdapter
import javax.inject.Inject

class FeedAdapter @Inject constructor(): BaseSortedPaginationRVAdapter<UserFeed>() {
    override val itemsComparator: (item1: UserFeed, item2: UserFeed) -> Int = { item1, item2 ->
        item2.createdAt.compareTo(item1.createdAt)
    }
    override val itemClass: Class<UserFeed> = UserFeed::class.java

    override fun createView(parent: ViewGroup, viewType: Int, inflater: LayoutInflater): BaseVH<UserFeed> {
        return FeedHolder.create(parent)
    }

    init {
        //todo add fields
        itemsTheSameComparator = { oldItem, newItem ->
            oldItem.id == newItem.id
                    && oldItem.createdAt == newItem.createdAt
                    && oldItem.text == newItem.text
        }

        itemsTheSameComparator = { oldItem, newItem ->
            oldItem.id == newItem.id
        }

        dataStorage = SortedDataStorage(itemClass, this)
    }
}