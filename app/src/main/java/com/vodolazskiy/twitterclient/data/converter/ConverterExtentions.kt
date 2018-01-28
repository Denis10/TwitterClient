package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.IConvertersContext
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlin.reflect.KClass

/**
 * Created by denis on 1/28/18.
 */
//////////////////////////////////////// FLOWABLE EXTENSIONS ///////////////////////////////////////

fun <IN : Any, OUT : Any> Flowable<IN>.convert(converter: IConvertersContext, outClass: KClass<OUT>): Flowable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Flowable<Iterable<IN>>.convertCollection(converter: IConvertersContext, outClass: KClass<OUT>):
        Flowable<List<OUT>> {
    return map { converter.convertCollection(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Observable<IN>.convert(converter: IConvertersContext, outClass: KClass<OUT>): Observable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Observable<Iterable<IN>>.convertCollection(converter: IConvertersContext, outClass: KClass<OUT>):
        Observable<List<OUT>> {
    return map { converter.convertCollection(it, outClass.java) }
}

//use reified for less params count

inline fun <reified OUT : Any> Flowable<*>.convert(converter: IConvertersContext): Flowable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any, IN: Any, InList: Iterable<IN>> Flowable<InList>.convertCollection(converter: IConvertersContext):
        Flowable<List<OUT>> {
    return map { converter.convertCollection(it as Iterable<Any>, OUT::class.java) }
}

inline fun <reified OUT : Any> Observable<*>.convert(converter: IConvertersContext): Observable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <IN: Any, reified OUT : Any> Observable<Iterable<IN>>.convertCollection(converter: IConvertersContext):
        Observable<List<OUT>> {
    return map { converter.convertCollection(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Maybe<*>.convert(converter: IConvertersContext): Maybe<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Maybe<Iterable<*>>.convertCollection(converter: IConvertersContext):
        Maybe<List<OUT>> {
    return map { converter.convertCollection(it as Iterable<Any>, OUT::class.java) }
}

inline fun <IN : Any, reified OUT : Any> IConvertersContext.convertToOut(inEntity: IN): OUT {
    return convert(inEntity, OUT::class.java)
}

inline fun <IN : Any, reified OUT : Any, InList: Iterable<IN>> IConvertersContext.convertCollectionToOut(inEntities: InList): List<OUT> {
    return convertCollection(inEntities, OUT::class.java)
}

////////////////////////////////////////////////////////////////////////////////////////////////////