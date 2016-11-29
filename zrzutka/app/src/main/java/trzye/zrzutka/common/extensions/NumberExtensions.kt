package trzye.zrzutka.common.extensions

fun Double.toReadablePriceString() = String.format( "%.2f", this ).replace(',', '.')

fun Long.toMoneyDouble(): Double = this/100.0

fun Double.toMoneyLong(): Long = (this * 100).toLong()

