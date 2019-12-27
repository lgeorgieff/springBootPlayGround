# Variance
Generic types in Java are **invariant**, i.e. `List<String>` is not a subtype of `List<Object>`. Otherwise the following would be possible:
```
List<String> strs = new ArrayList<String>();
List<Object> objs = strs;
objs.add(1);
String str = strs.get(0);
```

To solve this problem Java introduced the `<? extends T>` type argument which allows to specify that a type argument is either of type T or any subtype of it. This also says that we can safely read values from a source (e.g. list) since we know that it is either T or a subtype of T. But we cannot write values to the source since we do not know which objects comply to the unknown subtype of T. Thus the wildcard `<? extends T>` makes `List<String>` **covariant** of `List<Object>`.

In addition Java allows the type argument `<? super String>` that says that a sink only accepts object of type String or its super type(s). This allows to write Strings into a sink while at the same time returning Objects. In contrast to **covariant** this concept is called **contravariance**.

These concepts are alo known as **PECS - Producer-Extends, Consumer-Super**.

# Declaration-Site-Variance
## out Type Parameters
The **out** keyword makes a parameter type **invariant**.

The general rule is: when a type parameter T of a class C is declared out, it may occur only in out-position in the members of C, but in return `C<Base>` can safely be a supertype of `C<Derived>`. [1]

Sample code is available at [outParameter.kt](./src/main/kotlin/com/example/kotlingenerics/outParameter.kt).
```kotlin
class ProducerOut<out T>(value: T) {
    private var _t = value
    fun get(): T = _t
}

fun main() {
    val stringProducerOut = ProducerOut("string")
    val objectProducerOut: ProducerOut<Any> = stringProducerOut
    assertTrue(objectProducerOut is ProducerOut<Any>)
}
```

## in Type Parameter
The **in** keyword makes a parameter type **contravariant**

In addition to **out**, Kotlin provides a complementary variance annotation: **in**. It makes a type parameter **contravariant**: it can only be consumed and never produced. [1]

Sample code is available at [inParameter.kt](./src/main/kotlin/com/example/kotlingenerics/inParameter.kt).
```kotlin
class ConsumerIn<in T>(value: T) {
    private var _t = value
    fun set(t: T) { _t = t }
}
fun mainInParameter() {
    val numberConsumerIn = ConsumerIn<Number>(123)
    val doubleConsumerIn: ConsumerIn<Double> = numberConsumerIn
    assertTrue(doubleConsumerIn is ConsumerIn<Double>)
}
```

# Type Projections
## out
Some types cannot be declared neither **covariant** nor **contravariant**, such as Array. Depending on the operation `<T>` must be **covariant** or **contravariant** depending on the operation.

Here we want to prevent `copyOut` from writing data into `from` and still want to copy data from `from` to `to`.

Sample code is available at [outProjections.kt](./src/main/kotlin/com/example/kotlingenerics/outProjections.kt).
```kotlin
fun copyOut(from: Array<out Any>, to: Array<Any?>) {
    assertEquals(from.size, to.size)
    for(i in from.indices) to[i] = from[i]
}

fun mainOutProjections() {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val anys: Array<Any?> = arrayOfNulls(3)
    copyOut(ints, anys)
    assertEquals(ints[0], anys[0])
    assertEquals(ints[1], anys[1])
    assertEquals(ints[2], anys[2])
}
```

## in
**in** ensures that `dest` accepts `Int` or any super type of int.

Sample code is available at [inProjections.kt](./src/main/kotlin/com/example/kotlingenerics/inProjections.kt).
```kotlin
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
```

# Star Projection
Sample code is available at [starProjection.kt](./src/main/kotlin/com/example/kotlingenerics/starProjection.kt).

# Type Erasure
Sample code is available at [runtime.kt](./src/main/kotlin/com/example/kotlingenerics/runtime.kt).

# References
1: https://kotlinlang.org/docs/reference/generics.html#declaration-site-variance