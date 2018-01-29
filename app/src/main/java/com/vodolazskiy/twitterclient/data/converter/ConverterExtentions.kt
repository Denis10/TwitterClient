package com.vodolazskiy.twitterclient.data.converter

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.CompositeConverter
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction3


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

inline fun <reified IN : Any, reified OUT : Any> ConvertersContext.registerConverter(
        converter: KFunction3<
                @ParameterName(name = "input") IN,
                @ParameterName(name = "token") Any?,
                @ParameterName(name = "convertersContext") ConvertersContext,
                OUT>) {
    registerConverter(IN::class.java, OUT::class.java, converter)
}

fun <IN : Any, OUT : Any> ConvertersContext.registerConverter(
        inClass: Class<IN>,
        outClass: Class<OUT>,
        converter: (IN, Any?, ConvertersContext) -> OUT) {
    registerConverter(inClass, outClass, converter::invoke)
}

inline fun <IN : Any, OUT : Any> ConvertersContext.registerConverter(
        inClass: Class<IN>,
        outClass: Class<OUT>,
        crossinline converter: (IN, ConvertersContext) -> OUT) {
    val converterWrapper = { input: IN, _: Any?, context: ConvertersContext ->
        converter.invoke(input, context)
    }
    registerConverter(inClass, outClass, converterWrapper::invoke)
}

inline fun <IN : Any, OUT : Any> ConvertersContext.registerConverter(
        inClass: Class<IN>,
        outClass: Class<OUT>,
        crossinline converter: (IN) -> OUT) {
    val wrapper = { input: IN, _: Any?, _: ConvertersContext ->
        converter.invoke(input)
    }
    registerConverter(inClass, outClass, wrapper::invoke)
}

inline fun <reified IN : Any, reified OUT : Any> ConvertersContext.registerConverter(
        crossinline converter: (IN) -> OUT) {
    val wrapper = { input: IN, _: Any?, _: ConvertersContext ->
        converter.invoke(input)
    }
    registerConverter(IN::class.java, OUT::class.java, wrapper::invoke)
}

/**
 * Registers converters.
 *
 * @param visitorClass reference to [CompositeConverter] cass with default constructor.
 */
fun ConvertersContext.registerConverter(visitorClass: Class<out CompositeConverter>) {
    visitorClass.getDeclaredConstructor().newInstance().register(this)
}

/**
 * Registers converters.
 *
 * @param visitor instance of converters context visitor
 */
fun ConvertersContext.registerConverter(visitor: CompositeConverter) {
    visitor.register(this)
}