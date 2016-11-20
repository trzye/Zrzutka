package trzye.zrzutka.model.entity

import android.graphics.Color
import android.graphics.drawable.Drawable
import trzye.zrzutka.R
import java.util.*

fun randColor() = Random().nextInt(16)+1
fun getColor(id: Int): Int {
    return when (id) {
        1  -> Color.parseColor("#EF5350")
        2  -> Color.parseColor("#F06292")
        3  -> Color.parseColor("#BA68C8")
        4  -> Color.parseColor("#9575CD")
        5  -> Color.parseColor("#7986CB")
        6  -> Color.parseColor("#2196F3")
        7  -> Color.parseColor("#039BE5")
        8  -> Color.parseColor("#0097A7")
        9  -> Color.parseColor("#009688")
        10 -> Color.parseColor("#43A047")
        11 -> Color.parseColor("#689F38")
        12 -> Color.parseColor("#827717")
        13 -> Color.parseColor("#EF6C00")
        14 -> Color.parseColor("#FF5722")
        15 -> Color.parseColor("#A1887F")
        16 -> Color.parseColor("#757575")
        else -> Color.parseColor("EF5350")
    }
}