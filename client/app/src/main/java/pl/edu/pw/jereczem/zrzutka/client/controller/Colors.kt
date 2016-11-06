package pl.edu.pw.jereczem.zrzutka.client.controller

import pl.edu.pw.jereczem.zrzutka.client.R
import java.util.*

fun randColor() = Random().nextInt(16)+1
fun setColor(id: Int): Int {
    return when (id) {
        1 -> R.color.color1
        2 -> R.color.color2
        3 -> R.color.color3
        4 -> R.color.color4
        5 -> R.color.color5
        6 -> R.color.color6
        7 -> R.color.color7
        8 -> R.color.color8
        9 -> R.color.color9
        10 -> R.color.color10
        11 -> R.color.color11
        12 -> R.color.color12
        13 -> R.color.color13
        14 -> R.color.color14
        15 -> R.color.color15
        16 -> R.color.color16
        else -> R.color.color1
    }
}