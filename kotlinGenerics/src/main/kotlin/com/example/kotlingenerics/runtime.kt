package com.example.kotlingenerics

import kotlin.test.assertEquals

// fun <T> Iterable<*>.filterIsInstance() = filter { it is T }
inline fun <reified T> Iterable<*>.filterIsInstance() = filter { it is T }

fun mainRuntime() {
    val lst = listOf(1, 2, "a", "b", 3, "c")
    val ints = lst.filterIsInstance<Int>()
    assertEquals(ints.size, 3)
    assertEquals(ints[0], 1)
    assertEquals(ints[1], 2)
    assertEquals(ints[2], 3)
}
