package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlin.reflect.KClass

//todo remove unused functions
//////////////////////////////////////// FLOWABLE EXTENSIONS ///////////////////////////////////////

fun <IN : Any, OUT : Any> Flowable<IN>.convert(converter: ConvertersContext, outClass: KClass<OUT>): Flowable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Flowable<Iterable<IN>>.convertCollection(converter: ConvertersContext, outClass: KClass<OUT>):
        Flowable<List<OUT>> {
    return map { converter.convertCollection(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Observable<IN>.convert(converter: ConvertersContext, outClass: KClass<OUT>): Observable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Observable<Iterable<IN>>.convertCollection(converter: ConvertersContext, outClass: KClass<OUT>):
        Observable<List<OUT>> {
    return map { converter.convertCollection(it, outClass.java) }
}

//use reified for less params count

inline fun <reified OUT : Any> Flowable<*>.convert(converter: ConvertersContext): Flowable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any, IN : Any, InList : Iterable<IN>> Flowable<InList>.convertCollection(converter: ConvertersContext):
        Flowable<List<OUT>> {
    return map { converter.convertCollection(it as Iterable<Any>, OUT::class.java) }
}

inline fun <reified OUT : Any> Observable<*>.convert(converter: ConvertersContext): Observable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <IN : Any, reified OUT : Any> Observable<Iterable<IN>>.convertCollection(converter: ConvertersContext):
        Observable<List<OUT>> {
    return map { converter.convertCollection(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Maybe<*>.convert(converter: ConvertersContext): Maybe<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Maybe<Iterable<*>>.convertCollection(converter: ConvertersContext):
        Maybe<List<OUT>> {
    return map { converter.convertCollection(it as Iterable<Any>, OUT::class.java) }
}

inline fun <IN : Any, reified OUT : Any> ConvertersContext.convertToOut(inEntity: IN): OUT {
    return convert(inEntity, OUT::class.java)
}

inline fun <IN : Any, reified OUT : Any, InList : Iterable<IN>> ConvertersContext.convertCollectionToOut(inEntities: InList): List<OUT> {
    return convertCollection(inEntities, OUT::class.java)
}

////////////////////////////////////////////////////////////////////////////////////////////////////