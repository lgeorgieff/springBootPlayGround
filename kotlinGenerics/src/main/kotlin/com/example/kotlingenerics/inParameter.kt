package com.example.kotlingenerics

import kotlin.test.assertTrue

// Declaration-Site-Variance
// <in T> makes this meta-type contravariant, e.g. ConsumerIn<Number> and ConsumerIn<Double>. Since in allows T to be
// consumed only and not to be produced, it is possible to ensure that T won't be misused as any other type than T (or
// a super type of T.
class ConsumerIn<in T>(value: T) {
    private var _t = value
    fun set(t: T) { _t = t }
    // fun get(): T = _t
}

class Consumer<T>(value: T) {
    private var _t = value
    fun set(t: T) { _t = t }
    fun get(): T = _t
}

fun mainInParameter() {
    val numberConsumerIn = ConsumerIn<Number>(123)
    val doubleConsumerIn: ConsumerIn<Double> = numberConsumerIn

    assertTrue(doubleConsumerIn is ConsumerIn<Double>)

    val numberConsumer = Consumer<Number>(123)
    // val doubleConsumer: Consumer<Double> = numberConsumer
}
