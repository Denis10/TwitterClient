package com.vodolazskiy.twitterclient.presentation.screens.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vodolazskiy.twitterclient.domain.converter.models.UserFeed
import com.vodolazskiy.twitterclient.presentation.base.adapter.BaseSortedRecyclerViewAdapter
import javax.inject.Inject

class FeedAdapter @Inject constructor() : BaseSortedRecyclerViewAdapter<UserFeed>() {

    override val itemsComparator: (item1: UserFeed, item2: UserFeed) -> Int = { item1, item2 ->
        item2.createdAt.compareTo(item1.createdAt)
    }

    override fun createView(parent: ViewGroup, viewType: Int, inflater: LayoutInflater): BaseVH<UserFeed> {
        return FeedHolder.create(parent)
    }

    override fun getItemId(position: Int): Long {
        val pos = convertAdapterPositionToDataIndex(position)
        return if (pos == -1) {
            position.toLong()
        } else {
            dataStorage.get(pos).id
        }
    }

    init {
        contentTheSameComparator = { oldItem, newItem ->
            oldItem.id == newItem.id
                    && oldItem.createdAt == newItem.createdAt
                    && oldItem.text == newItem.text
                    && oldItem.mediaUrlHttps == newItem.mediaUrlHttps
        }

        itemsTheSameComparator = { oldItem, newItem ->
            oldItem.id == newItem.id
        }

        dataStorage = SortedDataStorage(UserFeed::class.java, this)
    }
}
