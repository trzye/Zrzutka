package trzye.zrzutka.model.entity.charge

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.model.entity.purchase.Purchase
import javax.persistence.*

@Entity
class Charge private constructor(
        @Id @GeneratedValue val id: Long? = null,
        amountToPay: Double,
        amountPaid: Double,
        @OneToOne val charged: Contributor,
        @OneToOne val purchase: Purchase
) : BaseObservable(), Copyable<Charge>{

    private constructor() : this(Contributor(Friend(), Contribution("")), Purchase("", 0.0, Contribution("")))
    constructor(charged: Contributor, purchase: Purchase, amountToPay: Double = 0.0, amountPaid: Double = 0.0) : this(null,  amountToPay, amountPaid, charged, purchase)

    @Column var amountToPay: Double = amountToPay
        set(value) {field = value; notifyChange()}

    @Column var amountPaid: Double = amountPaid
        set(value) {field = value; notifyChange()}

    fun getReadableAmountPaid() = amountPaid.toReadablePriceString()
    fun getReadableAmountToPay() = amountToPay.toReadablePriceString()

    override fun doCopy(): Charge {
        return Charge(id, amountToPay, amountPaid, charged, purchase)
    }

}