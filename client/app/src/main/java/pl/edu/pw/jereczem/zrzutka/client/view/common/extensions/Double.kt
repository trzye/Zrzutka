package pl.edu.pw.jereczem.zrzutka.client.view.common.extensions

/**
 * Created by micha on 06.11.2016.
 */
fun Double.toReadablePriceString() = String.format( "%.2f", this ).replace(',', '.')