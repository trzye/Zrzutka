package trzye.zrzutka.common.extensions

fun String.createShort(): String {
    val split = this.split(" ")
    var initials = split.first().toUpperCase().getOrElse(0, { ' ' }).toString()
    if (split.size > 1)
        initials += split[1].toUpperCase().first()
    return initials
}