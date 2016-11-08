package trzye.zrzutka.common.extensions

import java.util.*

fun Date.addYears(years : Int){
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.YEAR, years)
    time = calendar.time.time
}