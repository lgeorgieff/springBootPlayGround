package com.example.kotlingenerics

import kotlin.test.assertTrue

// Declaration-Site-Variance
// <out T> makes this meta-type covariant, e.g. ProducerOut<String> and ProducerOut<Object>. Since out allows T to be
// produced only and not to be consumed, it is possible to ensure that T won't change to something else than T (or
// a subtype of T.
class ProducerOut<out T>(value: T) {
    private var _t = value
    fun get(): T = _t
    // fun set(t: T) { _t = t }
}

class Producer<T>(value: T) {
    private var _t = value
    fun get(): T = _t
    fun set(t: T) { _t = t }
}

fun mainOutParameter() {
    val stringProducerOut = ProducerOut("string")
    val objectProducerOut: ProducerOut<Any> = stringProducerOut
    assertTrue(objectProducerOut is ProducerOut<Any>)

    val stringProducer = Producer("string")
    // val objectProducer: Producer<Any> = stringProducer
}
