package trzye.zrzutka.common.extensions

import java.util.*

fun String.createShort(): String {
    try {
        val split = this.split(" ")
        var initials = split.first().toUpperCase().getOrElse(0, { ' ' }).toString()
        if (split.size > 1)
            initials += split[1].toUpperCase().first()
        return initials
    } catch (e : NoSuchElementException){
        return " "
    }
}