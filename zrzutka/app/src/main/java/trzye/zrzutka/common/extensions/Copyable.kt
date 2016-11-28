package trzye.zrzutka.common.extensions

interface Copyable<out T> {
    fun doCopy(): T
}

fun <E : Copyable<E>> Collection<E>.doCopy(): MutableCollection<E> {
    val clone = mutableListOf<E>()
    this.forEach { clone.add(it.doCopy()) }
    return clone
}

fun <E : Copyable<E>> MutableList<E>.doCopy(): MutableList<E> {
    val clone = mutableListOf<E>()
    this.forEach { clone.add(it.doCopy()) }
    return clone
}