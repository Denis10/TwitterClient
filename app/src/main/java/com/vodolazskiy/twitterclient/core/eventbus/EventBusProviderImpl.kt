package com.vodolazskiy.twitterclient.core.eventbus

import com.vodolazskiy.twitterclient.core.observeMain
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class EventBusProviderImpl @Inject constructor() : EventBusProvider {
    private val mBusSubject = PublishSubject.create<Any>()

    private fun toObservable(): Observable<Any> {
        return mBusSubject.hide()
    }

    override fun <T : BusEvent> register(eventClass: Class<T>): Observable<T> {
        return toObservable()
                .filter { event -> event.javaClass == eventClass }
                .cast(eventClass)
                .observeMain()
    }

    override fun <T : BusEvent> post(event: T) {
        mBusSubject.onNext(event)
    }
}