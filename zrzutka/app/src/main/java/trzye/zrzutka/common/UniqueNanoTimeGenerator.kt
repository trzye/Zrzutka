package trzye.zrzutka.common

object UniqueNanoTimeGenerator {

    var lastGeneratedValue: Long = 0L

    fun getUniqueValue(): Long = synchronized(this, {
        var res = System.nanoTime()
        if (res == lastGeneratedValue) res++
        lastGeneratedValue = res
        return res
    })
}