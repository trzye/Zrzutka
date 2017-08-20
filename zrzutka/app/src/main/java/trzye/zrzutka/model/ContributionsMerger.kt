package trzye.zrzutka.model

import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.addContributor
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase

object ContributionsMerger {

    fun merge(contributionsToMerge: MutableList<Contribution>): Contribution {
        val newContribution = Contribution("Nowe złączenie")

        newContribution.addContributors(contributionsToMerge)
        newContribution.addPurchases(contributionsToMerge.flatMap { it._purchases })

        return newContribution
    }
}

private fun Contribution.addContributors(contributionsToMerge: MutableList<Contribution>) {
    contributionsToMerge.forEach {
        it._contributors.forEach {
            contributor ->
            if (friendInNewContributionNotFound(contributor)) {
                val toAdd = Contributor(contributor.friend.doCopy())
                addContributor(toAdd)
            }
        }
    }
}

private fun Contribution.friendInNewContributionNotFound(contributor: Contributor) = this._contributors.find { contributor.friend.databasePojo().id == it.friend.databasePojo().id } == null

private fun Contribution.addPurchases(purchases: List<Purchase>) {
    purchases.forEach {
        this.addMergedPurchase(it)
    }
}

private fun Contribution.addMergedPurchase(mergedPurchase: Purchase) {
    val newPurchase = Purchase(mergedPurchase.name, mergedPurchase.price)
    newPurchase.colorId = mergedPurchase.colorId
    linkPurchase(newPurchase)
    this._contributors.forEach { contributor ->
        val newCharge = Charge()
        if(friendInMergedPurchaseFound(contributor, mergedPurchase)){
            val foundCharge = mergedPurchase._charges.find { it.charged?.friend?.databasePojo()?.id == contributor.friend.databasePojo().id }
            newCharge.amountPaid = foundCharge?.amountPaid ?: 0
            newCharge.amountToPay = foundCharge?.amountToPay ?: 0
        }
        linkChargeAndPurchase(newCharge, newPurchase)
        linkChargeAndContributor(contributor, newCharge)
    }
}

private fun linkChargeAndContributor(contributor: Contributor, newCharge: Charge) {
    newCharge.charged = contributor
    contributor._charges.add(newCharge)
}

private fun linkChargeAndPurchase(newCharge: Charge, newPurchase: Purchase) {
    newCharge.purchase = newPurchase
    newPurchase._charges.add(newCharge)
}

private fun friendInMergedPurchaseFound(contributor: Contributor, mergedPurchase: Purchase)
        = mergedPurchase._charges.flatMap { listOf(it.charged) }.find { contributor.friend.databasePojo() == it?.friend?.databasePojo() } != null

private fun Contribution.linkPurchase(newPurchase: Purchase) {
    this._purchases.add(newPurchase)
    newPurchase.contribution = this
}

