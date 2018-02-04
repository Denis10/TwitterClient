package com.vodolazskiy.twitterclient.presentation.base.adapter

import android.support.v4.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.setRefreshLock(canRefresh: () -> Boolean) {
    setOnChildScrollUpCallback { _, target ->
        when {
            !canRefresh.invoke() -> true
            else -> target?.canScrollVertically(-1) ?: false
        }
    }
}