package trzye.zrzutka.model.entity.charge

import android.databinding.BaseObservable
import trzye.zrzutka.common.UniqueNanoTimeGenerator
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
import java.util.*
import javax.persistence.*

@Entity
class Charge private constructor(
        @Id @GeneratedValue val id: Long? = null,
        amountToPay: Double,
        amountPaid: Double,
        @OneToOne var charged: Contributor? = null,
        @OneToOne var purchase: Purchase? = null
) : BaseObservable(), Copyable<Charge>{

    private constructor() : this(0.0)
    constructor(amountToPay: Double = 0.0, amountPaid: Double = 0.0) : this(null,  amountToPay, amountPaid)

    @Column val chargeUniqueNumberForSorting: Long  = UniqueNanoTimeGenerator.getUniqueValue()

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

