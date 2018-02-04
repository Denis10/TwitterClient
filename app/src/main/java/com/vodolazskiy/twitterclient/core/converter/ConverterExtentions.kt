package com.vodolazskiy.twitterclient.core.converter

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlin.reflect.KClass

//////////////////////////////////////// FLOWABLE EXTENSIONS ///////////////////////////////////////

fun <IN : Any, OUT : Any> Flowable<IN>.convert(converter: ConvertersContext, outClass: KClass<OUT>): Flowable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

fun <IN : Any, OUT : Any> Observable<IN>.convert(converter: ConvertersContext, outClass: KClass<OUT>): Observable<OUT> {
    return map { converter.convert(it, outClass.java) }
}

//use reified for less params count

inline fun <reified OUT : Any> Flowable<*>.convert(converter: ConvertersContext): Flowable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Observable<*>.convert(converter: ConvertersContext): Observable<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <reified OUT : Any> Maybe<*>.convert(converter: ConvertersContext): Maybe<OUT> {
    return map { converter.convert(it, OUT::class.java) }
}

inline fun <IN : Any, reified OUT : Any> ConvertersContext.convertToOut(inEntity: IN): OUT {
    return convert(inEntity, OUT::class.java)
}

inline fun <IN : Any, reified OUT : Any, InList : Iterable<IN>> ConvertersContext.convertCollectionToOut(inEntities: InList): List<OUT> {
    return convertCollection(inEntities, OUT::class.java)
}

////////////////////////////////////////////////////////////////////////////////////////////////////

inline fun <IN : Any, OUT : Any> ConvertersContext.registerConverter(
        inClass: Class<IN>,
        outClass: Class<OUT>,
        crossinline converter: (IN) -> OUT) {
    val wrapper = { input: IN, _: Any?, _: ConvertersContext ->
        converter.invoke(input)
    }
    registerConverter(inClass, outClass, wrapper::invoke)
}

/**
 * Registers converters.
 *
 * @param visitor instance of converters context visitor
 */
fun ConvertersContext.registerConverter(visitor: CompositeConverter) {
    visitor.register(this)
}