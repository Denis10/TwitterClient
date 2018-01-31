package com.vodolazskiy.twitterclient.presentation.base.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import java.util.*

fun RecyclerView.getLastVisibleItemPosition(): Int {
    val lManager = layoutManager
    return when (lManager) {
        null -> throw IllegalStateException("Layout manager of recyclerView $this is null!")
        is LinearLayoutManager -> lManager.findLastVisibleItemPosition()
        is StaggeredGridLayoutManager -> {
            val into = lManager.findLastVisibleItemPositions(null)
            val intoList = into.toList()
            return Collections.max(intoList)
        }
        else -> throw IllegalStateException("Unsupported layout manager of recyclerView $this")
    }
}

fun RecyclerView.getFirstVisibleItemPosition(): Int {
    val lManager = layoutManager
    return when (lManager) {
        null -> throw IllegalStateException("Layout manager of recyclerView $this is null!")
        is LinearLayoutManager -> lManager.findFirstVisibleItemPosition()
        is StaggeredGridLayoutManager -> {
            val into = lManager.findFirstVisibleItemPositions(null)
            Collections.min(into.toList())
        }

        else -> throw IllegalStateException("Unsupported layout manager of recyclerView $this")
    }
}

fun RecyclerView.isReverseLayout(): Boolean {
    val lManager = layoutManager
    return when (lManager) {
        null -> throw IllegalStateException("Layout manager of recyclerView $this is null!")
        is LinearLayoutManager -> lManager.stackFromEnd
        is StaggeredGridLayoutManager -> false

        else -> throw IllegalStateException("Unsupported layout manager of recyclerView $this")
    }
}