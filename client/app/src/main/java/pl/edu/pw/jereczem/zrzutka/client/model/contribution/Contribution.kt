package pl.edu.pw.jereczem.zrzutka.client.model.contribution

import java.util.*
import javax.persistence.*

@Entity
data class Contribution private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @Column var title: String ="",
        @Column(columnDefinition = "DATE") var startDate: Date = Date(),
        @Column(columnDefinition = "DATE") var endDate: Date = Date()
//        @OneToOne val summary: Summary? = null
) {

    private constructor() : this(null)
    constructor(title: String, startDate: Date = Date(), endDate: Date = startDate) : this(null, title, startDate, endDate)

    @ManyToOne private val _contributors: MutableCollection<Contributor> = mutableListOf()
    val contributors: List<Contributor> get() = _contributors.toList()

    @ManyToOne private val _purchases: MutableCollection<Purchase> = mutableListOf()
    val purchases: List<Purchase> get() = _purchases.toList()

    fun addContributor(contributor: Contributor): Unit {
        _contributors.add(contributor)
        contributor.contribution = this
        _purchases.forEach { purchase ->
            purchase.addCharge(Charge(contributor, purchase))
        }
    }

    fun removeContributor(contributor: Contributor) {
        _contributors.remove(contributor)
        contributor.contribution = null
        _purchases.forEach { purchase ->
            purchase.charges.filter { charge -> charge.charged == contributor }.forEach {
                contributorCharge ->
                purchase.removeCharge(contributorCharge)
            }
        }
    }

    fun addPurchase(purchase: Purchase) {
        _purchases.add(purchase)
        purchase.contribution = this
        _contributors.forEach { contributor ->
            purchase.addCharge(Charge(contributor, purchase, purchase.price/_contributors.size))
        }
    }

    fun removePurchase(purchase: Purchase) {
        _purchases.remove(purchase)
        purchase.contribution = null
    }

}

class Summary {

}





