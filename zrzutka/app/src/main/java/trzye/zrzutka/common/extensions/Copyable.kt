package trzye.zrzutka.common.extensions

interface Copyable<out T> {
    fun doCopy(): T
}

fun <E : Copyable<E>> MutableCollection<E>.doCopy(): MutableCollection<E> {
    val clone = mutableListOf<E>()
    this.forEach { clone.add(it.doCopy()) }
    return clone
}