package trzye.zrzutka.model.entity.contribution

import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
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

fun Contribution.addContributor(contributor: Contributor) {
    _contributors.add(contributor)
}

fun Contribution.removeContributor(contributor: Contributor) {
    _contributors.remove(contributor)
}

fun Contribution.removePurchase(purchase: Purchase) {
    this._purchases.remove(purchase)
}

fun Contribution.addPurchase(purchase: Purchase) {
    this._purchases.add(purchase)
}