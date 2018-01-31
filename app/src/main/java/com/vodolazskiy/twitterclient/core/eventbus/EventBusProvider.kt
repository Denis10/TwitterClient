package com.vodolazskiy.twitterclient.core.eventbus

import io.reactivex.Observable

interface EventBusProvider {

    fun <T : BusEvent> post(event: T)

    fun <T : BusEvent> register(eventClass: Class<T>): Observable<T>
}
