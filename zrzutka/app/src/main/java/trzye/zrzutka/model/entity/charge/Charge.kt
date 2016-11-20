package trzye.zrzutka.model.entity.charge

import trzye.zrzutka.common.extensions.Cloneable
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
import javax.persistence.*

@Entity
data class Charge private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @OneToOne var charged: Contributor? = null,
        @OneToOne var purchase: Purchase? = null,
        @Column var amountToPay: Double = 0.0,
        @Column val amountPaid: Double = 0.0
) :Cloneable<Charge>{
    private constructor() : this(null)
    constructor(charged: Contributor, purchase: Purchase, amountToPay: Double = 0.0, amountPaid: Double = 0.0)
        : this(null, charged, purchase, amountToPay, amountPaid)

    override fun clone(): Charge {
        return copy()
    }
}