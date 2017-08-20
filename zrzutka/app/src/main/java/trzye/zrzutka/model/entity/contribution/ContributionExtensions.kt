package trzye.zrzutka.model.entity.contribution

import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
import java.util.*

fun Contribution.changeTitle(title: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
    if (title.trim().isEmpty())
        onFailure()
    else {
        this.title = title
        onSuccess()
    }
}

fun Contribution.changeStartDate(date: Date) {
    startDate = date
    if (startDate > endDate)
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
    contributor.contribution = this
    _contributors.add(contributor)
    _purchases.forEach {
        purchase ->
        val newCharge = if (purchase._charges.isEmpty()) Charge(purchase.price, purchase.price) else Charge(0, 0)
        link(contributor, newCharge, purchase)
    }
}

fun Contribution.removeContributor(contributor: Contributor) {
    contributor.contribution = null
//    contributor.removeFromContribution()
    _contributors.remove(contributor)
    contributor._charges.forEach {
        charge ->
        charge.charged = null

        charge.purchase?.let {
            it._charges.remove(charge)
            val n = it.charges.size
            it._charges.forEach {
                it.amountPaid = it.amountPaid + charge.amountPaid / n
                it.amountToPay = it.amountToPay + charge.amountToPay / n
            }
        }

        charge.purchase = null
//        charge.removeFromContributorAndPurchase()
    }
    contributor._charges.clear()
}

fun Contribution.removePurchase(purchase: Purchase) {
    _purchases.remove(purchase)
    purchase.contribution = null
//    purchase.removeFromContribution()
}

fun Contribution.addPurchase(purchase: Purchase) {
    purchase.contribution = this
    _purchases.add(purchase)
    _contributors.forEach {
        contributor ->
        val newCharge = Charge(purchase.price / contributors.size, purchase.price / contributors.size)
        link(contributor, newCharge, purchase)
    }
}

private fun link(contributor: Contributor, newCharge: Charge, purchase: Purchase) {
    purchase._charges.add(newCharge)
    contributor._charges.add(newCharge)
    newCharge.purchase = purchase
    newCharge.charged = contributor
}