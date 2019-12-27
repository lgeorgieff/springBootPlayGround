package com.example.kotlingenerics

// With star projections we don't care about the actual type.
// It is only allowed to read the value but not to write anything to the container.
fun printArray(arr: Array<*>) {
    for (a in arr) println(a)

    // arr[0] = 1 as Any?
}

fun starProjections() {
    val arr = arrayOf(1, 2, 3)
    printArray(arr)
}
