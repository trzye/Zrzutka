package trzye.zrzutka.common

object UniqueNanoTimeGenerator {

    private var lastGeneratedValue: Long? = null

    fun getUniqueValue(): Long = synchronized(this, {
        var res = System.nanoTime()
        if (res == lastGeneratedValue) res++
        lastGeneratedValue = res
        return res
    })
}