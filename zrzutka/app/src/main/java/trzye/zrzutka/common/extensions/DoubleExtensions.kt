package trzye.zrzutka.common.extensions

fun Double.toReadablePriceString() = String.format( "%.2f", this ).replace(',', '.')