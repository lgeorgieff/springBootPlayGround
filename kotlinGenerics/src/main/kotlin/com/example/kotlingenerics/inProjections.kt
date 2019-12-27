package com.example.kotlingenerics

import kotlin.test.assertEquals

fun fill(dest: Array<in Int>, value: Int) {
    for (i in dest.indices) dest[i] = value
}

fun mainInProjections() {
    val objects: Array<Any?> = arrayOfNulls(3)
    fill(objects, 8)
    assertEquals(objects[0], 8)
    assertEquals(objects[1], 8)
    assertEquals(objects[2], 8)
}
