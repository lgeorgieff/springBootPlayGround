package com.example.kotlingenerics

import kotlin.test.assertEquals

fun <T : Comparable<T>> sort(list: List<T>) = list.sorted()

fun mainConstraints() {
    val listOfInts = listOf(5, 2, 3, 4, 1)
    val sorted = sort(listOfInts)
    assertEquals(sorted, listOf(1, 2, 3, 4, 5))
}
