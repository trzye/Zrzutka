package trzye.zrzutka.model.entity.charge

import android.databinding.BaseObservable
import trzye.zrzutka.common.UniqueNanoTimeGenerator
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase

class Charge private constructor(
        amountToPay: Long,
        amountPaid: Long,
        var charged: Contributor? = null,
        var purchase: Purchase? = null,
        private var jooqCharge: trzye.zrzutka.jooq.model.tables.pojos.Charge? = null
) : BaseObservable(), Copyable<Charge>{

    private constructor() : this(0)
    constructor(amountToPay: Long = 0, amountPaid: Long = 0) : this(amountToPay, amountPaid, jooqCharge = null)

    val chargeUniqueNumberForSorting = UniqueNanoTimeGenerator.getUniqueValue()

    var amountToPay = amountToPay
        set(value) {field = value; notifyChange()}

    var amountPaid = amountPaid
        set(value) {field = value; notifyChange()}

    fun getReadableAmountPaid() = amountPaid.toMoneyDouble().toReadablePriceString()
    fun getReadableAmountToPay() = amountToPay.toMoneyDouble().toReadablePriceString()

    override fun doCopy(): Charge {
        return Charge(amountToPay, amountPaid, charged, purchase, jooqCharge)
    }

    fun setBy(charge: Charge) {
        amountToPay = charge.amountToPay
        amountPaid = charge.amountPaid
        charged = charge.charged
        purchase = charge.purchase
    }

    constructor(
            jooqCharge: trzye.zrzutka.jooq.model.tables.pojos.Charge,
            jooqCharged: Contributor?,
            jooqPurchase: Purchase?
    ) : this(
            jooqCharge.amounttopay,
            jooqCharge.amountpaid,
            jooqCharged,
            jooqPurchase,
            jooqCharge = jooqCharge
    )

    fun databasePojo() = trzye.zrzutka.jooq.model.tables.pojos.Charge(
            amountPaid,
            amountToPay,
            chargeUniqueNumberForSorting,
            jooqCharge?.chargedId,
            jooqCharge?.id,
            jooqCharge?.purchaseId
    ).also { jooqCharge = it }

    fun setId(id: Int): trzye.zrzutka.jooq.model.tables.pojos.Charge {
        jooqCharge?.id =id
        return databasePojo()
    }

    fun setContributorId(contributor: Contributor) {
        jooqCharge?.chargedId = contributor.databasePojo().id.toLong()
    }

    fun setPurchaseId(purchase: Purchase) {
        jooqCharge?.purchaseId = purchase.databasePojo().id.toLong()
    }

}

