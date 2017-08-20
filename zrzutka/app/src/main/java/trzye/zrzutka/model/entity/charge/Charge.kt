package trzye.zrzutka.model.entity.charge

import android.databinding.BaseObservable
import trzye.zrzutka.common.UniqueNanoTimeGenerator
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.constructOrNull
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.purchase.Purchase
import javax.persistence.*

@Entity
class Charge private constructor(
        @Id @GeneratedValue val id: Long? = null,
        amountToPay: Long,
        amountPaid: Long,
        @OneToOne var charged: Contributor? = null,
        @OneToOne var purchase: Purchase? = null,
        jooqCharge: trzye.zrzutka.jooq.model.tables.pojos.Charge? = null
) : BaseObservable(), Copyable<Charge>{

    val jooqCharge = jooqCharge ?: trzye.zrzutka.jooq.model.tables.pojos.Charge(
            amountPaid,
            amountToPay,
            UniqueNanoTimeGenerator.getUniqueValue(),
            charged?.id,
            id?.toInt(),
            purchase?.id
    )

    constructor(
            jooqCharge: trzye.zrzutka.jooq.model.tables.pojos.Charge,
            jooqCharged: Contributor?,
            jooqPurchase: Purchase?
    ) : this(
            jooqCharge.id?.toLong(),
            jooqCharge.amounttopay,
            jooqCharge.amountpaid,
            jooqCharged,
//            constructOrNull(jooqCharged, {Contributor(it)}),
            jooqPurchase,
            jooqCharge = jooqCharge
    )



    private constructor() : this(0)
    constructor(amountToPay: Long = 0, amountPaid: Long = 0) : this(null,  amountToPay, amountPaid)

    @Column val chargeUniqueNumberForSorting: Long  = UniqueNanoTimeGenerator.lastGeneratedValue

    @Column var amountToPay: Long = amountToPay
        set(value) {field = value; notifyChange()}

    @Column var amountPaid: Long = amountPaid
        set(value) {field = value; notifyChange()}

    fun getReadableAmountPaid() = amountPaid.toMoneyDouble().toReadablePriceString()
    fun getReadableAmountToPay() = amountToPay.toMoneyDouble().toReadablePriceString()

    override fun doCopy(): Charge {
        return Charge(id, amountToPay, amountPaid, charged, purchase, jooqCharge)
    }

    fun setBy(charge: Charge) {
        amountToPay = charge.amountToPay
        amountPaid = charge.amountPaid
        charged = charge.charged
        purchase = charge.purchase
    }

}

