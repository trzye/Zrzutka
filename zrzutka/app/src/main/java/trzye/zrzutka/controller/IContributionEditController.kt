package trzye.zrzutka.controller

import java.util.*

interface IContributionEditController {
    fun changeTitle(title: String)
    fun changeStartDate(date: Date)
    fun changeEndDate(date: Date)
}
