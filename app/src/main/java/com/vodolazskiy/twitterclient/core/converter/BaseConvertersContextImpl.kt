package com.vodolazskiy.twitterclient.core.converter


import com.vodolazskiy.twitterclient.core.L
import kotlin.reflect.KFunction3

//todo simplify it
abstract class BaseConvertersContextImpl : ConvertersContext {
    private val converters = HashMap<Class<*>, HashMap<Class<*>, KFunction3<*, Any?, ConvertersContext, *>>>()

    override fun <IN : Any, OUT : Any> registerConverter(inClass: Class<IN>, outClass: Class<OUT>, converter: KFunction3<IN, Any?, ConvertersContext, OUT>) {
        var out: Class<*>? = outClass
        do {
            registerConverterInner(inClass, out as Class<Any>, converter as KFunction3<IN, Any?, ConvertersContext, Any>)
            out = out.superclass
        } while (out != null && out != Any::class.java)

        for (outInterface in outClass.interfaces) {
            registerConverterInner(inClass, outInterface as Class<Any>, converter as KFunction3<IN, Any?, ConvertersContext, Any>)
        }
    }

    override fun registerConverter(visitorClass: Class<out ConvertersContextVisitor>) {
        registerConverter(visitorClass.getDeclaredConstructor().newInstance())
    }

    override fun registerConverter(visitor: ConvertersContextVisitor) = visitor.visit(this)

    override fun <IN : Any, OUT : Any> convert(input: IN, token: Any?, outClass: Class<OUT>): OUT {
        if (outClass.isInstance(input)) {
            @Suppress("UNCHECKED_CAST")
            return input as OUT
        }
        val inClass = input.javaClass
        return getConverter(inClass, outClass).invoke(input, token, this)
    }

    override fun <IN : Any, OUT : Any> convert(input: IN, outClass: Class<OUT>): OUT = convert(input, Unit, outClass)

    override fun <IN : Any, OUT : Any> convertCollection(collection: Iterable<IN>, token: Any?, outClass: Class<OUT>): List<OUT> =
            collection.map { convert(it, token, outClass) }

    override fun <IN : Any, OUT : Any> convert(outClass: Class<OUT>, token: Any?): (input: IN) -> OUT = {
        convert(it, token, outClass)
    }

    override fun <IN : Any, OUT : Any> convert(outClass: Class<OUT>): (input: IN) -> OUT = {
        convert(it, outClass)
    }

    override fun <IN : Any, OUT : Any> convertCollection(collection: Iterable<IN>, outClass: Class<OUT>): List<OUT> =
            convertCollection(collection, Unit, outClass)


    override fun <IN : Any, OUT : Any> convertCollection(outClass: Class<OUT>): (input: Iterable<IN>) -> List<OUT> = {
        convertCollection(it, outClass)
    }

    private fun <IN : Any, OUT : Any> getConverter(
            inClass: Class<IN>,
            outClass: Class<OUT>): KFunction3<IN, Any?, ConvertersContext, OUT> {

        var input: Class<*>? = inClass
        do {
            val fromConverters = converters[input]
            if (fromConverters != null) {
                val entityConverter = fromConverters[outClass]
                if (entityConverter != null) {
                    return entityConverter as KFunction3<IN, Any?, ConvertersContext, OUT>
                }
            }

            input = input?.superclass
        } while (input != null)

        //search by interfaces
        inClass.interfaces
                .asSequence()
                .mapNotNull { converters[it] }
                .mapNotNull { it[outClass] }
                .forEach {
                    return it as KFunction3<IN, Any?, ConvertersContext, OUT>
                }

        converterNotFound(inClass, outClass)
    }

    private fun <IN : Any, OUT : Any> registerConverterInner(inClass: Class<IN>, outClass: Class<OUT>, converter: KFunction3<IN, Any?, ConvertersContext, OUT>) {
        L.i(TAG, "Register converter " + converter.javaClass.simpleName + " as " + inClass.simpleName + " -> " + outClass.simpleName)

        var innerMap = converters[inClass]
        if (innerMap == null) {
            innerMap = java.util.HashMap()
            converters[inClass] = innerMap
        }

        val existConverter = innerMap[outClass]
        if (existConverter != null) {
            val exception = IllegalStateException(
                    "DataConverter " + inClass.simpleName +
                            " -> " + outClass.simpleName +
                            " is already registered as " + existConverter.javaClass.canonicalName +
                            ". So, converter " + converter.javaClass.canonicalName + " cannot be registered!")
            L.e(TAG, exception)
            throw exception
        }

        innerMap[outClass] = converter
    }

    private fun <IN : Any, OUT : Any> converterNotFound(inClass: Class<IN>, outClass: Class<OUT>): Nothing {
        val exception = UnsupportedOperationException(TAG + " There is no available converters between " +
                inClass.canonicalName + " and " + outClass.canonicalName)
        L.e(TAG, exception)
        throw exception
    }

    companion object {
        private val TAG = BaseConvertersContextImpl::class.java.simpleName
    }
}