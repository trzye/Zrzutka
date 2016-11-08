package trzye.zrzutka.model.extensions

import trzye.zrzutka.model.entity.Contribution
import java.util.*

fun Contribution.changeTitle(title: String, onSuccess: () -> Unit, onFailure: () -> Unit ) {
    if(title.trim().isEmpty())
        onFailure()
    else {
        this.title = title
        onSuccess()
    }
}

fun Contribution.changeStartDate(date: Date) {
    startDate = date
    if(startDate > endDate)
        endDate = Date(startDate.time)
}

fun Contribution.changeEndDate(date: Date) {
    endDate = date
}

fun Contribution.copyBaseData(source: Contribution) {
    title = source.title
    endDate = Date(source.endDate.time)
    startDate = Date(source.startDate.time)
}
