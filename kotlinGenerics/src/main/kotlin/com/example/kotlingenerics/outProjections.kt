package com.example.kotlingenerics

import kotlin.test.assertEquals

fun copyOut(from: Array<out Any>, to: Array<Any?>) {
    assertEquals(from.size, to.size)
    for (i in from.indices) to[i] = from[i]
}

fun copy(from: Array<Any>, to: Array<Any?>) {
    assertEquals(from.size, to.size)
    for (i in from.indices) to[i] = from[i]
}

fun mainOutProjections() {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val anys: Array<Any?> = arrayOfNulls(3)

    copyOut(ints, anys)
    assertEquals(ints[0], anys[0])
    assertEquals(ints[1], anys[1])
    assertEquals(ints[2], anys[2])

    // copy(ints, anys)
}
