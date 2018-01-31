package com.vodolazskiy.twitterclient.core

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

val WORKER_SCHEDULER = Schedulers.io()
val MAIN_SCHEDULER = AndroidSchedulers.mainThread()!!

fun <T> Observable<T>.ioToMain(): Observable<T> = compose { subscribeOn(WORKER_SCHEDULER).observeOn(MAIN_SCHEDULER) }
fun <T> Observable<T>.subscribeAsync(): Observable<T> = subscribeOn(WORKER_SCHEDULER)
fun <T> Observable<T>.onErrorResumeNextKt(f: ((t: Throwable) -> ObservableSource<T>)): Observable<T> = onErrorResumeNext(Function { f.invoke(it) })
fun <T> Observable<T>.observeMain(): Observable<T> = observeOn(MAIN_SCHEDULER)