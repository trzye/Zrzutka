package trzye.zrzutka.common.extensions

interface Cloneable<out T> {
    fun clone(): T
}

fun <E : Cloneable<E>> MutableCollection<E>.clone(): MutableCollection<E> {
    val clone = mutableListOf<E>()
    this.forEach { clone.add(it.clone()) }
    return clone
}