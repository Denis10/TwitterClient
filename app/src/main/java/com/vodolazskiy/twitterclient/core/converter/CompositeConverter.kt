package com.vodolazskiy.twitterclient.core.converter

interface CompositeConverter {
    fun register(convertersContext: ConvertersContext)
}
